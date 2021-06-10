package Stage2;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class PedestrianView {
    private Pedestrian person;
    private Rectangle view;
    private Circle viewc;
    private final double SIZE = 5;

    public PedestrianView(Comuna comuna, Pedestrian p) {
        person = p;
        view = new Rectangle(SIZE, SIZE, Color.BLUE);
        view.setX(person.getX() - SIZE / 2);   // Rectangle x position is the X coordinate of the
        // upper-left corner of the rectangle
        view.setY(person.getY() - SIZE / 2); // Rectangle y position is the Y coordinate of the
        // upper-left corner of the rectangle
        viewc = new Circle(view.getX()+SIZE/2,view.getY()+SIZE/2,SIZE/2,Color.rgb(255,0,0,0.0));
        comuna.getView().getChildren().addAll(view);
        comuna.getView().getChildren().addAll(viewc);
    }
    public void update() {
        view.setX(person.getX() - SIZE / 2);
        view.setY(person.getY() - SIZE / 2);
        viewc.setCenterX(view.getX()+SIZE/2);
        viewc.setCenterY(view.getY()+SIZE/2);
    }
    public void recColor(Pedestrian p){
        view.setFill(Color.BROWN);
        viewc.setFill(Color.rgb(0, 0, 0, 0.0));
        view.setStroke(Color.rgb(0, 0, 0, 0));
        viewc.setStroke(Color.rgb(0, 0, 0, 0));
    }
    public void infectedColor(Pedestrian p, double y){
        viewc.setFill(Color.rgb(255,0,0,y));
        viewc.setStroke(Color.rgb(255, 0, 0, 1));
        view.setFill(Color.rgb(0, 0, 0, 0.0));
        view.setStroke(Color.rgb(0, 0, 0, 0));
    }
}
