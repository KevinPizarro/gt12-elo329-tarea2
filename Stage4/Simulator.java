package Stage3;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
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
    private static double delta_t; 
    /**
     * Nodos para darle color y estilo a la linea de los graficos 
     */
    private Node lines;
    private Node linei;
    private Node liner;
    /**
     * Nodos para darle color y estilo a el área de los graficos 
     */
    private Node fills;
    private Node filli;
    private Node fillr;

    /**
     * @param framePerSecond frequency of new views on screen
     * @param simulationTime2realTimeRate how faster the simulation runs relative to real time
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
     * Método para llamar a actualizar de estado y vista la comuna, por ende los individuos además de graficar
     */
    private void takeAction() {
        double nextStop=simulationTime+simulationSamplingTime;
        for(; simulationTime<nextStop; simulationTime+=delta_t) {
            comuna.computeNextState(delta_t); // compute its next state based on current global state
            comuna.updateState();            // update its state
            comuna.updateView();
            /**
            * Actualiza el grafico de áreas apiladas
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
     * Método para iniciar la simulación
     */
    public void start(){
        stop();
        Stage3.restart();
        SimulatorConfig.stopflag = false;
        animation.play();
        Stage3.primary.getScene().addEventFilter(KeyEvent.KEY_PRESSED, e->keyHandle(e));
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
        SimulatorConfig.stopflag = true;
        animation.stop();
        Stage3.primary.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, e->keyHandle(e));
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
