package Stage1;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;

public class Simulator {
    private Timeline animation;
    private Stage1.Comuna comuna;
    private double simulationSamplingTime;
    private double simulationTime;  // it goes along with real time, faster or slower than real time
    private double delta_t;   // precision of discrete simulation time

    /**
     * @param framePerSecond frequency of new views on screen
     * @param simulationTime2realTimeRate how faster the simulation runs relative to real time
     */
    public Simulator (double framePerSecond, double simulationTime2realTimeRate, Stage1.Comuna comuna){
        this.comuna = comuna;
        double viewRefreshPeriod = 1 / framePerSecond; // in [ms] real time used to display
        // a new view on application
        simulationSamplingTime = viewRefreshPeriod *simulationTime2realTimeRate;
        delta_t = Stage1.SimulatorConfig.DELTA_T;
        simulationTime = 0;
        animation = new Timeline(new KeyFrame(Duration.millis(viewRefreshPeriod*1000), e->takeAction()));
        animation.setCycleCount(Timeline.INDEFINITE);
    }
    private void takeAction() {
        double nextStop=simulationTime+simulationSamplingTime;
        for(; simulationTime<nextStop; simulationTime+=delta_t) {
            comuna.computeNextState(delta_t); // compute its next state based on current global state
            comuna.updateState();            // update its state
        }
        //???
    }
    public void start(){
        animation.play();
        comuna.getView().setOnKeyPressed( e->keyHandle(e));
    }
    private void keyHandle (KeyEvent e) {
        switch(e.getCode())
        {
            case RIGHT:
                speedup();
                break;
            case LEFT:
                slowdown();
                break;

        }
	/// ?????
    }
    public void stop(){
        animation.stop();
        comuna.getView();
    }
    public void speedup(){
        delta_t=2*delta_t;
    }
    public void slowdown(){
        delta_t=delta_t/2;
    }
}
