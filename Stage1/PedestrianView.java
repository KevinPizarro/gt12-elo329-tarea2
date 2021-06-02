package Stage1;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class PedestrianView {
    private Stage1.Pedestrian person;
    private Rectangle view;
    private final double SIZE = 5;

    public PedestrianView(Stage1.Comuna comuna, Stage1.Pedestrian p) {
        person = p;
        view = new Rectangle(SIZE, SIZE, Color.BLUE);
        view.setX(person.getX() - SIZE / 2);   // Rectangle x position is the X coordinate of the
        // upper-left corner of the rectangle
        view.setY(person.getY() - SIZE / 2); // Rectangle y position is the Y coordinate of the
        // upper-left corner of the rectangle
        comuna.getView().getChildren().addAll(view);
    }

    public void update() {
        view.setX(person.getX());
        view.setY(person.getY());
    }
}
