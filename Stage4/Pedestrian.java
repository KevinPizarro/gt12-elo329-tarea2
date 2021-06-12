package Stage4;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;

/**
 * Clase de los individuos, contiene todos la información del individuo, asi como los metodos que utilizan al mismo
 */
public class Pedestrian {
    /**
     * Dirección del archivo de audio
     */	
    private static final String MEDIA_URL = "beep.mp3";
    /**
     * Posición en el eje X del individuo
     */
    private double x;
    /**
     * Posición en el eje Y del individuo
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
     * Variación máxima del ángulo del individuo
     */
    private double deltaAngle;
    /**
     * Variable auxiliar para la nueva posición en X 
     */
    private double x_tPlusDelta;
     /**
     * Variable auxiliar para la nueva posición en Y
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
     * @param deltaAngle Variación maxima del ángulo del individuo
     */
    public Pedestrian(Comuna comuna, double speed, double deltaAngle){
        this.comuna = comuna; //Asociamos la comuna al individuo
        double s = (Math.random()*0.2) + 0.9; //Calculamos el factor que escala la velocidad,entre 0.9 y 1.1
        this.speed = s*speed; //Calculamos la velocidad del individuo
        this.deltaAngle=deltaAngle; //Asociamos la variación del ángulo
        x = Math.random()*comuna.getWidth(); //Calculamos la posición inicial en X 
        y = Math.random()*comuna.getHeight(); //Calculamos la posición inicial en Y
        angle = Math.random()*2*Math.PI; //Calculamos el ángulo inicial
        this.state = State.S; /El estado inicial del individuo es susceptible
        pedestrianView = new PedestrianView(comuna,this); //Creamos la representación gráfica del individuo
        this.rec_time = 0; //Iniciamos el tiempo de recuperación en 0 ya que no esta infectado
        this.mask = false; //Inicializa al individuo sin mascarilla
    }

    /**
     * Método para colocar la mascarilla al individuo
     */
    public void putmask(){
        this.mask = true;
        this.pedestrianView.maskBorder(this);
    }

    /**
     * Método para saber si el individuo porta mascarilla
     */
    public boolean getmask(){
        return mask;
    }

    /**
     * Método para obtener la posición en X del individuo
     * @return double con la posición en X del individuo
     */   
    public double getX(){
        return x;
    }

    /**
     * Método para obtener la posición en Y del individuo
     * @return double con la posición en Y del individuo
     */
    public double getY() {
        return y;
    }

    /**
     * Método para calcular el siguiente estado del individuo
     * @param delta_t Cantidad de tiempo que ha pasado desde el calculo anterior 
     */
    public void computeNextState(double delta_t) {
        double r=Math.random(); //Escalar para multiplicar la variación del ángulo
        angle+=deltaAngle*(1-2*r); //Calculamos el nuevo ángulo
        x_tPlusDelta=x+speed*Math.cos(angle)*delta_t; //Calculamos la nueva posición en X
        y_tPlusDelta=y+speed*Math.sin(angle)*delta_t; //Calculamos la nueva posición en Y
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
            if(0>=rec_time) { //Si el tiempo de recuperación llega a 0 (o menos) el individuo se recupera
                state = State.R;  //recuperado
                rec_time = 0;
                this.pedestrianView.recColor(this); //Cambiamos la representación gráfica del individuo para reflejar su recuperación
            }
        }
    }

    /**
     * Método para actualizar la posición del individuo
     */
    public void updateState(){
        x=x_tPlusDelta;
        y=y_tPlusDelta;
    }
    /**
     * Método para actualizar la posición del individuo gráficamente
     */
    public void updateView() {
        this.pedestrianView.update();
    }

    /**
     * Método para infectar al individuo
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
     * Método para vacunar al individuo
     */
    public void vaccine(){
        if(state == State.S){
            state = State.V;
            this.pedestrianView.vaccinedColor(this);
        }
    }
    /**
     * Método para obtener el estado de salud actual del individuo
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

