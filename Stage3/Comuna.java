package Stage3;
import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
/**
 * Genera la comuna donde se realizará el análisis
 */

public class Comuna {
    /**
     * Arreglo de individuos
     */
    private ArrayList<Pedestrian> personas;
    /**
     * Dimensiones del territorio de la comuna
     */
    private Rectangle2D territory;
    /**
     * Representación visual de la comuna
     */
    private ComunaView view;
    /**
     * Pane correspondiente al gráfico.
     */
    private Pane graph;

    public Comuna(){
        /**
         * Constructor de la clase Comuna
         */
        double width = SimulatorConfig.WIDTH;
        double length = SimulatorConfig.LENGTH;
        territory = new Rectangle2D(0,0, width, length);
        double speed = SimulatorConfig.SPEED;
        double deltaAngle = SimulatorConfig.DELTA_THETA;
        view = new ComunaView(this);
        /**
         * Crea el arreglo de individuos
         */
        personas = new ArrayList<Pedestrian>(SimulatorConfig.N);
        /**
         * Infecta individuos según los datos del archivo
         */
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

    /**
     *
     * @return Ancho de la comuna
     */
    public double getWidth() {
        return territory.getWidth();
    }

    /**
     *
     * @return Alto de la comuna
     */
    public double getHeight() {
        return territory.getHeight();
    }

    /**
     * revisa el estado de los individuos y los actualiza, calculando cuando un individuo se contagia
     * @param delta_t variación de tiempo con el que se calcula el siguiente estado
     */
    public void computeNextState (double delta_t) {
        for(int i=0;i < personas.size();i++){
            for(int j=0; j< personas.size();j++){
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
        /**
         * calcula el estado de cada individuo
         */
        for(int i=0; i<personas.size();i++) {
            personas.get(i).computeNextState(delta_t);
        }
    }

    /**
     * Actualiza el estado de cada idividuo
     */
    public void updateState () {
        for(int i=0; i<personas.size();i++){
            personas.get(i).updateState();
        }
    }
    /**
     * Actualiza la representación grafica de la comuna
     */
    public void updateView(){
        view.update();
    }

    /**
     *
     * @return Arreglo de individuos
     */
    public ArrayList<Pedestrian> getPedestrian() {
        return personas;
    }

    /**
     *
     * @return Estado de la representación grafica de la comuna
     */
    public Group getView() {
        return view;
    }

    /**
     *
     * @return Pane del gráfico
     */
    public Pane getGraph(){
        return graph;
    }

    /**
     *
     * @return Cantidad de personas susceptibles
     */
    public int getSus(){
        int sus=0;
        State status;
        for(int i=0; i<personas.size();i++){
            status=personas.get(i).getState();
            if(status == State.S){
                sus++;
            }
        }
        System.out.println(sus);
        return sus;
    }

    /**
     *
     * @return Cantidad de infectados
     */
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

    /**
     *
     * @return Cantidad de recuperados
     */
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
 }