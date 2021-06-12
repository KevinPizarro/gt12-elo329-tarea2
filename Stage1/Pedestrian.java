package Stage1;

/**
 * Clase de los individuos, contiene todos la informacion del individuo, asi como los metodos que utilizan al mismo
 */
public class Pedestrian {
    /**
     * Posicion en el eje X del individuo
     */
    private double x;
    /**
     * Posicion en el eje Y del individuo
     */
    private double y;
    /**
     * Velocidad del individuo
     */
    private double speed;
    /**
     * Angulo al que se dirige el individuo
     */
    private double angle;
    /**
     * Variacion maxima del angulo del individuo
     */
    private double deltaAngle;
    /**
     * Variable auxiliar para la nueva posicion en X
     */
    private double x_tPlusDelta;
    /**
     * Variable auxiliar para la nueva posicion en Y
     */
    private double y_tPlusDelta;
    /**
     * Comuna a la que pertenece el individuo
     */
    private Comuna comuna;
    /**
     * Icono que representa al individuo en la comuna
     */
    private PedestrianView pedestrianView;
    /**
     * Constructor de Pedestrian. Inicializa un nuevo individuo
     * @param comuna Comuna a la que pertenece el individuo
     * @param speed Velocidad a la que se va a mover el individuo
     * @param deltaAngle Variacion maxima del angulo del individuo
     */
    public Pedestrian(Comuna comuna, double speed, double deltaAngle){
        this.comuna = comuna;
        this.speed = speed;
        this.deltaAngle=deltaAngle;
        x = Math.random()*comuna.getWidth();
        y = Math.random()*comuna.getHeight();
        angle = Math.random()*2*Math.PI;
        pedestrianView = new PedestrianView(comuna,this);
    }
    /**
     * Metodo para obtener la posicion en X del individuo
     * @return double con la posicion en X del individuo
     */
    public double getX(){
        return x;
    }
    /**
     * Metodo para obtener la posicion en Y del individuo
     * @return double con la posicion en Y del individuo
     */
    public double getY() {
        return y;
    }
    /**
     * Metodo para calcular el siguiente estado del individuo
     * @param delta_t Cantidad de tiempo que ha pasado desde el calculo anterior 
     */
    public void computeNextState(double delta_t) {
        double r=Math.random();
        angle+=deltaAngle*(1-2*r);
        x_tPlusDelta=x+speed*Math.cos(angle)*delta_t;
        y_tPlusDelta=y+speed*Math.sin(angle)*delta_t;
        if(x_tPlusDelta < 0){   // rebound logic
            x_tPlusDelta=-x_tPlusDelta;
            angle=Math.PI-angle;
        }
        if(y_tPlusDelta < 0){
            y_tPlusDelta=-y_tPlusDelta;
            angle=2*Math.PI-angle;
        }
        if( x_tPlusDelta > comuna.getWidth()){
            x_tPlusDelta=2*comuna.getWidth()-x_tPlusDelta;
            angle=Math.PI-angle;
        }
        if(y_tPlusDelta > comuna.getHeight()){
            y_tPlusDelta=2*comuna.getHeight()-y_tPlusDelta;
            angle=2*Math.PI-angle;
        }
    }
    /**
     * Metodo para actualizar la posicion del individuo
     */
    public void updateState(){
        x=x_tPlusDelta;
        y=y_tPlusDelta;
    }
    /**
     * Metodo para actualizar la posicion del individuo graficamente
     */
    public void updateView() {
        pedestrianView.update();
    }
}
