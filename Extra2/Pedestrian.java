package Extra2;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;

/**
 * Clase de los individuos, contiene todos la informacion del individuo, asi como los metodos que utilizan al mismo
 */
public class Pedestrian {
    /**
     * Direccion del archivo de audio
     */	
    private static final String MEDIA_URL = "beep.mp3";
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
     * Variable que representa el estado de salud del individuo
     */
    private State state;
    /**
     * Tiempo faltante para que el individuo se recupere
     */
    private double rec_time;
    /**
	 * Determina si el individuo lleva mascarilla o no
     */
    private boolean mask;

    /**
     * Constructor de Pedestrian. Inicializa un nuevo individuo
     * @param comuna Comuna a la que pertenece el individuo
     * @param speed Velocidad a la que se va a mover el individuo
     * @param deltaAngle Variacion maxima del angulo del individuo
     */
    public Pedestrian(Comuna comuna, double speed, double deltaAngle){
        this.comuna = comuna; //Asociamos la comuna al individuo
        double s = (Math.random()*0.2) + 0.9; //Calculamos el factor que escala la velocidad,entre 0.9 y 1.1
        this.speed = s*speed; //Calculamos la velocidad del individuo
        this.deltaAngle=deltaAngle; //Asociamos la variacion del angulo
        x = Math.random()*comuna.getWidth(); //Calculamos la posicion inicial en X 
        y = Math.random()*comuna.getHeight(); //Calculamos la posicion inicial en Y
        angle = Math.random()*2*Math.PI; //Calculamos el angulo inicial
        this.state = State.S; //El estado inicial del individuo es susceptible
        pedestrianView = new PedestrianView(comuna,this); //Creamos la representacion grafica del individuo
        this.rec_time = 0; //Iniciamos el tiempo de recuperacion en 0 ya que no esta infectado
        this.mask = false; //Inicializa al individuo sin mascarilla
    }

    /**
     * Metodo para colocar la mascarilla al individuo
     */
    public void putmask(){
        this.mask = true;
        this.pedestrianView.maskBorder(this);
    }

    /**
     * Metodo para saber si el individuo porta mascarilla
     * @return true or false dependiendo si el individuo tiene o no mascarilla respectivamente
     */
    public boolean getmask(){
        return mask;
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
        double r=Math.random(); //Escalar para multiplicar la variacion del angulo
        angle+=deltaAngle*(1-2*r); //Calculamos el nuevo angulo
        x_tPlusDelta=x+speed*Math.cos(angle)*delta_t; //Calculamos la nueva posicion en X
        y_tPlusDelta=y+speed*Math.sin(angle)*delta_t; //Calculamos la nueva posicion en Y
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
        if (state==State.I){   //revisa si el indiviuo cumple las condiciones de recuperacion
            rec_time -= delta_t;
            if(rec_time >= 0){
                this.pedestrianView.infectedColor(this, rec_time/SimulatorConfig.I_TIME); //Variamos la intensidad del color del individuo a medida que avanza el tiempo
            }
            if(0>=rec_time) { //Si el tiempo de recuperacion llega a 0 (o menos) el individuo se recupera
                state = State.R;  //recuperado
                rec_time = 0;
                this.pedestrianView.recColor(this); //Cambiamos la representacion grafica del individuo para reflejar su recuperacion
            }
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
        this.pedestrianView.update();
    }

    /**
     * Metodo para infectar al individuo
     */
    public void infect(){ //infecta individuo
        Media media = new Media(Paths.get(MEDIA_URL).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1); //MediaPlayer.ON
        if(state==State.S){
            state=State.I;
            mediaPlayer.play();
            this.pedestrianView.infectedColor(this,1.0);
        }
        this.rec_time=SimulatorConfig.I_TIME;
    }

    /**
     * Metodo para vacunar al individuo
     */
    public void vaccine(){
        if(state == State.S){
            state = State.V;
            this.pedestrianView.vaccinedColor(this);
        }
    }
    /**
     * Metodo para obtener el estado de salud actual del individuo
     * @return Retorna el estado de salud del individuo
     */
    public State getState(){ //retorna estado de persona
        return state;
    }
}
/**
 * Enum que nos permite tener los distintos estados de salud de los individuos
 */
enum State{
    S,I,R,V             //S para susceptibles, I para infectados, R para recuperados, V para vacunados
}

