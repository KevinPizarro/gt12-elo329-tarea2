package Stage1;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

/**
 * Genera la comuna donde se realizara el analisis
 */
public class Comuna {
    /**
     * Individuo que se encuentra en la comuna
     */
    private Pedestrian person;
    /**
     * Rectangulo que representa la comuna
     */
    private Rectangle2D territory; // Alternatively: double width, length;
                                   // but more methods would be needed.
    /**
     * Representacion grafica de la comuna
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
        view = new ComunaView(this); // What if you exchange this and the follow line?
        person = new Pedestrian(this, speed, deltaAngle);
    }
    /**
     * Metodo para obtener el ancho de la comuna
     * @return Ancho de la comuna como double 
     */
    public double getWidth() {
        return territory.getWidth();
    }
    /**
     * Metodo para obtener el alto de la comuna
     * @return Alto de la comuna como double 
     */
    public double getHeight() {
        return territory.getHeight();
    }
    /**
     * Revisa el estado de los individuos y los actualiza, calculando cuando un individuo se contagia
     * @param delta_t Variacion de tiempo con el que se calcula el siguiente estado
     */
    public void computeNextState (double delta_t) {
        person.computeNextState(delta_t);
    }
    /**
    * Actualiza el estado de cada idividuo
    */
    public void updateState () {
        person.updateState();
    }
    /**
     * Metodo que actualiza la representacion grafica de la comuna y todo en su interior
     */
    public void updateView(){
        view.update();
    }
    /**
     * Metodo que entrega el arreglo de personas pertenecientes a la comuna 
     * @return Pedestrian del individuo
     */
    public Pedestrian getPedestrian() {
        return person;
    }
    /**
     * Metodo para obtener la representacion grafica de la comuna
     * @return Group que contiene la representacion grafica de la comuna
     */
    public Group getView() {
        return view;
    }
 }
