package Stage2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;

/**
 * Clase cuya funcionalidad es dar inicio, parar, acelerar, ralentizar y coordinar todos los elementos necesarios
 * para la simulación.
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
     * Tiempo de muestreo para la simulación, tiempo entre delta_t
     */
    private double simulationSamplingTime;
    /**
     * Tiempo total de simulación
     */
    private double simulationTime;
    /**
     * Corresponde al paso de tiempo
     */
    private static double delta_t;   // precision of discrete simulation time

    /**
     * Constructor de la clase con 3 parámetros
     * @param framePerSecond Frecuencia para la actualización de cada vista gráfica
     * @param simulationTime2realTimeRate Representa que tan rápido se ejecuta la simulación respecto al tiempo real
     * @param comuna Representa a la comuna de la simulación.
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
     * Método para llamar a actualizar de estado y vista la comuna, por ende los individuos
     */
    private void takeAction() {
        double nextStop=simulationTime+simulationSamplingTime;
        for(; simulationTime<nextStop; simulationTime+=delta_t) { //Ciclo for que se ejecuta hasta el tiempo simulationTime
            comuna.computeNextState(delta_t); // Computa su nuevo estado
            comuna.updateState();            // Actualiza su nuevo estado
            comuna.updateView();            // Muesta su nuevo estado
        }
    }

    /**
     * Método para iniciar la simulación
     */
    public void start(){
        stop();
        Stage2.restart();
        SimulatorConfig.stopflag = false;
        animation.play();
        Stage2.primary.getScene().addEventFilter(KeyEvent.KEY_PRESSED, e->keyHandle(e));
    }

    /**
     * Método para leer input de teclado, llamando a acelerar o ralentizar la simulación según el caso
     * @param e Tecla presionada por el usuario
     */
    private void keyHandle (KeyEvent e) {
        switch (e.getCode()){
            case RIGHT: //Si se detecta la flecha derecha aumentamos la velocidad de simulación
                speedup();
                break;
            case LEFT: //Si se detecta la flecha izquierda disminuimos la velocidad de simulación
                slowdown();
                break;
            default: //Si no detecta ninguna de las anteriores mantiene el tiempo de simulación
                break;
        }
    }

    /**
     * Método para pausar la simulación
     */
    public void stop(){
        SimulatorConfig.stopflag = true; //Flag para determinar si se ha pausado
        animation.stop();
        Stage2.primary.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, e->keyHandle(e));
    }

    /**
     * Método para acelerar la simulación al doble de delta_t
     */
    public void speedup(){
       delta_t = delta_t*2;
       
    }

    /**
     * Método para ralentizar la simulación a la mitad de delta_t
     */
    public void slowdown(){
       delta_t = delta_t/2;
    }
}
