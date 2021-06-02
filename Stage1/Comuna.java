package Stage1;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class Comuna {
    private Stage1.Pedestrian person;
    private Rectangle2D territory; // Alternatively: double width, length;
                                   // but more methods would be needed.
    private Stage1.ComunaView view;
    private Pane graph;

    public Comuna(){
        double width = Stage1.SimulatorConfig.WIDTH;
        double length = Stage1.SimulatorConfig.LENGTH;
        territory = new Rectangle2D(0,0, width, length);
        double speed = Stage1.SimulatorConfig.SPEED;
        double deltaAngle = Stage1.SimulatorConfig.DELTA_THETA;
        view = new Stage1.ComunaView(this); // What if you exchange this and the follow line?
        person=new Stage1.Pedestrian(this, speed, deltaAngle);
        graph = new Pane();  // to be completed in other stages.
    }
    public double getWidth() {
        return this.territory.getWidth();
    }
    public double getHeight() {
        return this.territory.getHeight();
    }
    public void computeNextState (double delta_t) {
        this.person.computeNextState(delta_t);
    }
    public void updateState () {
        this.person.updateState();
    }
    public void updateView(){
        view.update();
    }
    public Stage1.Pedestrian getPedestrian() {
        return person;
    }
    public Group getView() {
        return view;
    }
    public Pane getGraph(){
        return graph;
    }
 }
