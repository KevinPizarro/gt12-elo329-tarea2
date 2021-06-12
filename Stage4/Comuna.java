package Stage4;
import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Comuna {
    private ArrayList<Pedestrian> personas;
    private ArrayList<Rectangle> vacunatorios;
    private Rectangle2D territory; // Alternatively: double width, length;
                                   // but more methods would be needed.
    private ComunaView view;
    private Pane graph;
    private boolean vacFlag; //Variable para confirmar la creación de los vacunatorios
    public Comuna(){
        double width = SimulatorConfig.WIDTH;
        double length = SimulatorConfig.LENGTH;
        territory = new Rectangle2D(0,0, width, length);
        double speed = SimulatorConfig.SPEED;
        double deltaAngle = SimulatorConfig.DELTA_THETA;
        view = new ComunaView(this); // What if you exchange this and the follow line?
        personas = new ArrayList<Pedestrian>(SimulatorConfig.N);
        vacFlag = true;
        for(int i=0;i<SimulatorConfig.N;i++){
            if(i<SimulatorConfig.I){
                Pedestrian p=new Pedestrian(this, speed, deltaAngle);
                personas.add(p);
                personas.get(i).infect();
                if(i < SimulatorConfig.I*SimulatorConfig.M){
                    personas.get(i).putmask();
                }
            }
            else{
                Pedestrian p=new Pedestrian(this, speed, deltaAngle);
                personas.add(p);
                if(i-SimulatorConfig.I < (SimulatorConfig.N-SimulatorConfig.I)*SimulatorConfig.M){
                    personas.get(i).putmask();
                }
            }
        }
        graph = new Pane();  // to be completed in other stages.
    }
    public double getWidth() {
        return territory.getWidth();
    }
    public double getHeight() {
        return territory.getHeight();
    }
    public void computeNextState (double delta_t, double simt) {
        if(SimulatorConfig.VAC_TIME <= simt && vacFlag){
            setvac();
            for(int i = 0; i < SimulatorConfig.NUM_VAC; i++){
                getView().getChildren().add(vacunatorios.get(i));
            }
            vacFlag = false;
        }
        for(int i=0;i < personas.size();i++){
            for(int j=0; j< personas.size();j++){
                if(simt >= SimulatorConfig.VAC_TIME){
                    if(personas.get(i).getState() == State.S && existvac(personas.get(i).getX(), personas.get(i).getY())){
                        personas.get(i).vaccine();
                    }                    
                }
                if(personas.get(i).getState()==State.S && personas.get(j).getState()==State.I){
                    double e = Math.sqrt(Math.pow(personas.get(i).getX()-personas.get(j).getX(),2)+Math.pow(personas.get(i).getY()-personas.get(j).getY(),2));
                    if(e < SimulatorConfig.D){
                        if((personas.get(i).getmask() == true) && (personas.get(j).getmask() == true)){
                            if(Math.random() <= SimulatorConfig.P2){
                                personas.get(i).infect();
                            }
                        }
                        else if(((personas.get(i).getmask() == true) && (personas.get(j).getmask() == false)) || ((personas.get(i).getmask() == false) && (personas.get(j).getmask() == true))){
                            if(Math.random() <= SimulatorConfig.P1){
                                personas.get(i).infect();
                            }
                        } 
                        else{
                            if(Math.random() <= SimulatorConfig.P0){
                                personas.get(i).infect();
                            }
                        }
                    }
                }
            }
        }
        for(int i=0; i<personas.size();i++) {
            personas.get(i).computeNextState(delta_t);
        }
    }
    public void updateState () {
        for(int i=0; i<personas.size();i++){
            personas.get(i).updateState();
        }
    }
    public void updateView(){
        view.update();
    }
    public ArrayList<Pedestrian> getPedestrian() {
        return personas;
    }
    public Group getView() {
        return view;
    }
    public Pane getGraph(){
        return graph;
    }
    public int getSus(){
        int sus=0;
        State status;
        for(int i=0; i<personas.size();i++){
            status=personas.get(i).getState();
            if(status == State.S){
                sus++;
            }
        }
        return sus;
    }
    public int getInf(){
        int inf=0;
        State status;
        for(int i=0; i<personas.size();i++){
            status=personas.get(i).getState();
            if(status == State.I){
                inf++;
            }
        }
        return inf;
    }
    public int getRec(){
        int rec=0;
        State status;
        for(int i=0; i<personas.size();i++){
            status=personas.get(i).getState();
            if(status == State.R){
                rec++;
            }
        }
        return rec;
    }
    public int getVac(){
        int vac = 0;
        State status;
        for(int i=0; i<personas.size();i++){
            status=personas.get(i).getState();
            if(status == State.V){
                vac++;
            }
        }
        return vac;
    }
    public void setvac(){
        double x;
        double y;
        boolean flag = false;
        int cont = 0;
        Rectangle vac;
        Rectangle vac1;
        do{ 
            vacunatorios = new ArrayList<Rectangle>(SimulatorConfig.NUM_VAC);
            cont = 0;
            flag = false;
            for(int i = 0; i < SimulatorConfig.NUM_VAC; i++){
                x = Math.random()*territory.getWidth();
                y = Math.random()*territory.getHeight();
                if(x >= territory.getWidth() - SimulatorConfig.VAC_SIZE){
                    x -= SimulatorConfig.VAC_SIZE;
                }
                if(y >= territory.getHeight() - SimulatorConfig.VAC_SIZE){
                    y -= SimulatorConfig.VAC_SIZE;
                }
                if(i == 0){
                    vac1 = new Rectangle(x, y, SimulatorConfig.VAC_SIZE, SimulatorConfig.VAC_SIZE);
                    vac1.setFill(Color.rgb(149, 255, 128, 0.5));
                    vacunatorios.add(vac1);
                }
                else{
                    vac = new Rectangle(x,y,SimulatorConfig.VAC_SIZE,SimulatorConfig.VAC_SIZE);
                    if(intervac(vac)){
                        while(intervac(vac)){
                            x = Math.random()*territory.getWidth();
                            y = Math.random()*territory.getHeight();
                            if(x >= territory.getWidth() - SimulatorConfig.VAC_SIZE){
                                x -= SimulatorConfig.VAC_SIZE;
                            }
                            if(y >= territory.getHeight() - SimulatorConfig.VAC_SIZE){
                                y -= SimulatorConfig.VAC_SIZE;
                            }
                            vac = new Rectangle(x, y, SimulatorConfig.VAC_SIZE, SimulatorConfig.VAC_SIZE);
                            cont++;
                            if (cont > 10){
                                flag = true;
                                break;
                            }
                        }
                    }
                    vac = new Rectangle(x, y, SimulatorConfig.VAC_SIZE, SimulatorConfig.VAC_SIZE);
                    vac.setFill(Color.rgb(149, 255, 128, 0.5));
                    vacunatorios.add(vac);
                }
            }
        } while (flag);
    }
    public boolean existvac(double x, double y){
        for(int i = 0; i < vacunatorios.size(); i++){
            if(vacunatorios.get(i).contains(x,y)){
                return true;
            }
        }
        return false;
    }
    public boolean intervac(Rectangle nuevo){
        for(int i = 0; i < vacunatorios.size(); i++){
            if(nuevo.intersects(vacunatorios.get(i).getX(),vacunatorios.get(i).getY(),vacunatorios.get(i).getWidth(),vacunatorios.get(i).getHeight())){
                return true;
            }
        }
        return false;
    }

 }
