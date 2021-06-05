package Stage1;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class PedestrianView {
    private Pedestrian person;
    private Rectangle view;
    private final double SIZE = 5;

    public PedestrianView(Comuna comuna, Pedestrian p) {
        person = p;
        view = new Rectangle(SIZE, SIZE, Color.BLUE);
        view.setX(person.getX() - SIZE / 2);   // Rectangle x position is the X coordinate of the
        // upper-left corner of the rectangle
        view.setY(person.getY() - SIZE / 2); // Rectangle y position is the Y coordinate of the
        // upper-left corner of the rectangle
        comuna.getView().getChildren().addAll(view);
    }

    public void update() {
        view.setX(person.getX() - SIZE / 2);
        view.setY(person.getY() - SIZE / 2);
    }
}
