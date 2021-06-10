package Stage2;
import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class Comuna {
    private ArrayList<Pedestrian> personas;
    private Rectangle2D territory; // Alternatively: double width, length;
                                   // but more methods would be needed.
    private ComunaView view;
    private Pane graph;

    public Comuna(){
        double width = SimulatorConfig.WIDTH;
        double length = SimulatorConfig.LENGTH;
        territory = new Rectangle2D(0,0, width, length);
        double speed = SimulatorConfig.SPEED;
        double deltaAngle = SimulatorConfig.DELTA_THETA;
        view = new ComunaView(this); // What if you exchange this and the follow line?
        personas = new ArrayList<Pedestrian>(SimulatorConfig.N);
        for(int i=0;i<SimulatorConfig.N;i++){
            if(i<SimulatorConfig.I){
                Pedestrian p=new Pedestrian(this, speed, deltaAngle);
                personas.add(p);
                personas.get(i).infect();
            }
            else{
                Pedestrian p=new Pedestrian(this, speed, deltaAngle);
                personas.add(p);
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
    public void computeNextState (double delta_t) {
        for(int i=0;i < personas.size();i++){
            for(int j=0; j< personas.size();j++){
                if(personas.get(i).getState()==State.S && personas.get(j).getState()==State.I){
                    double e = Math.sqrt(Math.pow(personas.get(i).getX()-personas.get(j).getX(),2)+Math.pow(personas.get(i).getY()-personas.get(j).getY(),2));
                    if(e < SimulatorConfig.D){
                        if(Math.random()<=SimulatorConfig.P0){
                            personas.get(i).infect();
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
    public String getIndState(){
        int sus=0,inf=0,rec=0;
        State status;
        for(int i=0; i<personas.size();i++){
            status=personas.get(i).getState();
            switch (status) {
                case R:
                    rec++;
                    break;
                case I:
                    inf++;
                    break;
                case S:
                    sus++;
                    break;
            }
        }
        String s = inf + ",\t" + rec + ",\t" + sus;
        return s;
    }
 }
