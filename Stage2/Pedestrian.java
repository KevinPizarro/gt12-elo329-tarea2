package Stage2;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;

public class Pedestrian {
    private static final String MEDIA_URL = "beep.mp3";
    private double x;
    private double y;
    private double speed;
    private double angle;
    private double deltaAngle;
    private double x_tPlusDelta;
    private double y_tPlusDelta;
    private Comuna comuna;
    private PedestrianView pedestrianView;
    private State state;
    private double rec_time;

    public Pedestrian(Comuna comuna, double speed, double deltaAngle){
        this.comuna = comuna;
        double s = (Math.random()*0.2) + 0.9;
        this.speed = s*speed;
        this.deltaAngle=deltaAngle;
        x = Math.random()*comuna.getWidth();
        y = Math.random()*comuna.getHeight();
        angle = Math.random()*2*Math.PI;
        this.state = State.S;
        pedestrianView = new PedestrianView(comuna,this);
        this.rec_time = 0;
    }
    public double getX(){
        return x;
    }
    public double getY() {
        return y;
    }

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
        if (state==State.I){   //revisa si el indiviuo cumple las condiciones de recuperacion
            rec_time -= delta_t;
            if(rec_time >= 0){
                this.pedestrianView.infectedColor(this, rec_time/SimulatorConfig.I_TIME);
            }
            if(0>=rec_time) {
                state = State.R;  //recuperado
                rec_time = 0;
                this.pedestrianView.recColor(this);
            }
        }
    }

    public void updateState(){
        x=x_tPlusDelta;
        y=y_tPlusDelta;
    }
    public void updateView() {
        this.pedestrianView.update();
    }
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
    public State getState(){ //retorna estado de persona
        return state;
    }
}
enum State{
    S,I,R             //S para susceptibles, I para infectados, R para recuperados
}

