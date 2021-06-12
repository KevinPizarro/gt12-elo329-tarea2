package Stage3;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
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
    private static double delta_t; 
    /**
     * Nodo para darle color y estilo a la linea de los susceptibles en el grafico
     */
    private Node lines;
    /**
     * Nodo para darle color y estilo a la linea de los infectados en el grafico
     */
    private Node linei;
    /**
     * Nodo para darle color y estilo a la linea de los recuperados en el grafico
     */
    private Node liner;
    /**
     * Nodo para darle color al area de los susceptibles en el grafico 
     */
    private Node fills;
    /**
     * Nodo para darle color al area de los infectadoss en el grafico 
     */
    private Node filli;
    /**
     * Nodo para darle color al area de los recuperados en el grafico 
     */
    private Node fillr;

    /**
     * Constructor de la clase Simulator
     * @param framePerSecond frequency of new views on screen
     * @param simulationTime2realTimeRate how faster the simulation runs relative to real time
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
     * Metodo para llamar a actualizar de estado y vista la comuna, ademas de actualizar el grafico
     */
    private void takeAction() {
        double nextStop=simulationTime+simulationSamplingTime;
        for(; simulationTime<nextStop; simulationTime+=delta_t) {
            comuna.computeNextState(delta_t); // compute its next state based on current global state
            comuna.updateState();            // update its state
            comuna.updateView();
            /**
            * Actualiza el grafico de areas apiladas
            */
            Stage3.infectados.getData().add(new XYChart.Data<Number,Number>(simulationTime,comuna.getInf()));
            Stage3.recuperados.getData().add(new XYChart.Data<Number,Number>(simulationTime,comuna.getRec()));
            Stage3.susceptibles.getData().add(new XYChart.Data<Number,Number>(simulationTime,comuna.getSus()));
            Stage3.areaChart.getData().remove(Stage3.infectados);
            Stage3.areaChart.getData().add(Stage3.infectados);
            Stage3.areaChart.getData().remove(Stage3.recuperados);
            Stage3.areaChart.getData().add(Stage3.recuperados);
            Stage3.areaChart.getData().remove(Stage3.susceptibles);
            Stage3.areaChart.getData().add(Stage3.susceptibles);
            fills = Stage3.susceptibles.getNode().lookup(".chart-series-area-fill");
            lines = Stage3.susceptibles.getNode().lookup(".chart-series-area-line");
            filli = Stage3.infectados.getNode().lookup(".chart-series-area-fill");
            linei = Stage3.infectados.getNode().lookup(".chart-series-area-line");
            fillr = Stage3.recuperados.getNode().lookup(".chart-series-area-fill");
            liner = Stage3.recuperados.getNode().lookup(".chart-series-area-line");
            filli.setStyle("-fx-fill: rgba(255,0,0,0.5);");
            linei.setStyle("-fx-stroke: rgba(255,0,0,1);");
            linei.setStyle("-fx-stroke-dash-array: 7 5 10 7;");
            fills.setStyle("-fx-fill: rgba(19,96,220,0.5);");
            lines.setStyle("-fx-stroke: rgba(19,96,220,1);");
            fillr.setStyle("-fx-fill: rgba(96,46,15,0.5);");
            liner.setStyle("-fx-stroke: rgba(96,46,15,1);");
            Stage3.areaChart.setStyle("CHART_COLOR_1: #FF0001 ; CHART_COLOR_2: #602E0F ; CHART_COLOR_3: #1360DC ;");
        }
    }

    /**
     * Metodo para iniciar la simulacion
     */
    public void start(){
        stop();
        Stage3.restart();
        SimulatorConfig.stopflag = false;
        animation.play();
        Stage3.primary.getScene().addEventFilter(KeyEvent.KEY_PRESSED, e->keyHandle(e));
    }

    /**
     * Metodo para leer input de teclado, llamando a acelerar o ralentizar la simulacion segun el caso
     * @param e Tecla presionada por el usuario
     */
    private void keyHandle (KeyEvent e) {
        switch (e.getCode()){
            case RIGHT: //Si se detecta la flecha derecha aumentamos la velocidad de simulacion
                speedup(); 
                break;
            case LEFT: //Si se detecta la flecha izquierda disminuimos la velocidad de simulacion
                slowdown();
                break;
            default: //Si no detecta ninguna de las anteriores mantiene el tiempo de simulacion
                break;
        }
    }

    /**
     * Metodo para pausar la simulacion
     */
    public void stop(){
        SimulatorConfig.stopflag = true;
        animation.stop();
        Stage3.primary.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, e->keyHandle(e));
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
