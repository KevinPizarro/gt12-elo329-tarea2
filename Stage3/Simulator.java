package Stage3;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.input.KeyEvent;


public class Simulator {
    private static Timeline animation;
    private Comuna comuna;
    private double simulationSamplingTime;
    private double simulationTime;  // it goes along with real time, faster or slower than real time
    private static double delta_t;   // precision of discrete simulation time
    private Node lines;
    private Node linei;
    private Node liner;
    private Node fills;
    private Node filli;
    private Node fillr;

    /**
     * @param framePerSecond frequency of new views on screen
     * @param simulationTime2realTimeRate how faster the simulation runs relative to real time
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
    private void takeAction() {
        double nextStop=simulationTime+simulationSamplingTime;
        for(; simulationTime<nextStop; simulationTime+=delta_t) {
            comuna.computeNextState(delta_t); // compute its next state based on current global state
            comuna.updateState();            // update its state
            comuna.updateView();
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
    public void start(){
        Stage3.restart();
        SimulatorConfig.stopflag = false;
        animation.play();
        Stage3.primary.getScene().addEventFilter(KeyEvent.KEY_PRESSED, e->keyHandle(e));
    }
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
    public void stop(){
        stop();
        SimulatorConfig.stopflag = true;
        animation.stop();
        Stage3.primary.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, e->keyHandle(e));
    }
    public void speedup(){
       delta_t = delta_t*2;
       
    }
    public void slowdown(){
       delta_t = delta_t/2;
    }
}
