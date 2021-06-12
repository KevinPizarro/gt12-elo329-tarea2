package Stage2;
import java.util.ArrayList;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

/**
 * Genera la comuna donde se realizara el análisis
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
    * Constructor de la clase Comuna
    */
    public Comuna(){
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
        * Infecta individuos según los datos del archivos
        */
        for(int i=0;i<SimulatorConfig.N;i++){ //Creamos a los individuos de la comuna
            if(i<SimulatorConfig.I){ //Creamos la cantidad indicada de individuos infectados
                Pedestrian p=new Pedestrian(this, speed, deltaAngle);
                personas.add(p);
                personas.get(i).infect();
            }
            else{ //Creamos los individuos susceptibles
                Pedestrian p=new Pedestrian(this, speed, deltaAngle);
                personas.add(p);
            }
        }


    }
    /**
     * Método para obtener el ancho de la comuna
     * @return Ancho de la comuna como double 
     */
    public double getWidth() {
        return territory.getWidth();
    }
    /**
     * Método para obtener el alto de la comuna
     * @return Alto de la comuna como double 
     */
    public double getHeight() {
        return territory.getHeight();
    }
    /**
     * Revisa el estado de los individuos y los actualiza, calculando cuando un individuo se contagia
     * @param delta_t Variación de tiempo con el que se calcula el siguiente estado
     * @param simt Tiempo actual de la simulación
     */
    public void computeNextState (double delta_t) {
        for(int i=0;i < personas.size();i++){
            for(int j=0; j< personas.size();j++){
                /**
                * revisa si hay individuos contagiadas cerca
                */
                if(personas.get(i).getState()==State.S && personas.get(j).getState()==State.I){
                    double e = Math.sqrt(Math.pow(personas.get(i).getX()-personas.get(j).getX(),2)+Math.pow(personas.get(i).getY()-personas.get(j).getY(),2));
                    /**
                    * si esta a distancia de contagio, se utiliza la probabilidad de contagio para ver si se infecta
                    */
                    if(e < SimulatorConfig.D){
                        if(Math.random()<=SimulatorConfig.P0){
                            personas.get(i).infect();
                        }
                    }
                }
            }
        }
        /**
        * calcula el estado de cada idividuo
        */
        for(int i=0; i<personas.size();i++) {
            personas.get(i).computeNextState(delta_t);
        }
    }
    /**
    * actualiza el estado de cada idividuo
    */
    public void updateState () {
        for(int i=0; i<personas.size();i++){
            personas.get(i).updateState();
        }
    }
    /**
     * Método que actualiza la representación gráfica de la comuna y todo en su interior
     */
    public void updateView(){
        view.update();
    }
    /**
     * Método que entrega el arreglo de personas pertenecientes a la comuna 
     * @return ArrayList<Pedestrian> de los individuos de la comuna
     */
    public ArrayList<Pedestrian> getPedestrian() {
        return personas;
    }

    /**
     * Método para obtener la representación gráfica de la comuna
     * @return Group que contiene la representación gráfica de la comuna
     */
    public Group getView() {
        return view;
    }

    /**
     * Método para obtener los estados de los indviduos en string
     * @return string con los estados de los individuos 
     */
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
