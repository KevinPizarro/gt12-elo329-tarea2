package Stage1;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;
/**
 * Clase cuya funcionalidad es dar inicio, parar, acelerar, ralentizar y coordinar todos los elementos necesarios
 * para la simulacion.
 */
public class Simulator {
    /**
     * Variable para animaciones, se actualiza cada delta_t
     */
    private static Timeline animation;
    /**
     * Variable para la comuna
     */
    private Comuna comuna;
    /**
     * Tiempo de muestreo para la simulacion, tiempo entre delta_t
     */
    private double simulationSamplingTime;
    /**
     * Tiempo total de simulacion
     */
    private double simulationTime;
    /**
     * Corresponde al paso de tiempo
     */
    private static double delta_t;   // precision of discrete simulation time

    /**
     * Constructor de la clase Simulator con 3 parametros
     * @param framePerSecond Frecuencia para la actualizacion de cada vista grafica
     * @param simulationTime2realTimeRate Representa que tan rapido se ejecuta la simulacion respecto al tiempo real
     * @param comuna Representa a la comuna de la simulacion.
     */
    public Simulator (double framePerSecond, double simulationTime2realTimeRate, Comuna comuna){
        this.comuna = comuna;
        double viewRefreshPeriod = 1 / framePerSecond; // in [ms] real time used to display
        // a new view on application
        simulationSamplingTime = viewRefreshPeriod *simulationTime2realTimeRate;
        delta_t = SimulatorConfig.DELTA_T;
        simulationTime = 0;
        animation = new Timeline(new KeyFrame(Duration.millis(viewRefreshPeriod*1000), e->takeAction()));
        animation.setCycleCount(Timeline.INDEFINITE);
    }
    /**
     * Metodo para llamar a actualizar de estado y vista la comuna, por ende los individuos
     */
    private void takeAction() {
        double nextStop=simulationTime+simulationSamplingTime;
        for(; simulationTime<nextStop; simulationTime+=delta_t) {
            comuna.computeNextState(delta_t); // compute its next state based on current global state
            comuna.updateState();            // update its state
            comuna.updateView();
        }
        
    }
    /**
     * Metodo para iniciar la simulacion
     */
    public void start(){
        animation.play();
        comuna.getView().setOnKeyPressed( e->keyHandle(e));
    }
    /**
     * Metodo para leer input de teclado, llamando a acelerar o ralentizar la simulacion segun el caso
     * @param e Tecla presionada por el usuario
     */
    private void keyHandle (KeyEvent e) {
        switch (e.getCode()){
            case RIGHT:
                speedup();
                break;
            case LEFT:
                slowdown();
                break;
            default:
                break;
        }
    }
    /**
     * Metodo para pausar la simulacion
     */
    public void stop(){
        animation.stop();
    }
    /**
     * Metodo para acelerar la simulacion al doble de delta_t
     */
    public void speedup(){
       delta_t = delta_t*2;
       
    }
    /**
     * Metodo para ralentizar la simulacion a la mitad de delta_t
     */
    public void slowdown(){
       delta_t = delta_t/2;
    }
}
