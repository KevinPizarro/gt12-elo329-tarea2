package Stage1;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


/**
 * Clase de la representacion grafica de los individos, permite representar a los individuos en la comuna, y manipular dicha representacion
 */
public class PedestrianView {
    /**
     * Individuo a representar
     */
    private Pedestrian person;
    /**
     * Representacion con forma de cuadrado
     */
    private Rectangle view;
    /**
     * Tamaño de la representacion
     */
    private final double SIZE = 5;

    /**
     * Constructor de PedestrianView. Inicializa una nueva representacion
     * @param comuna Comuna a la que pertenece el individuo a representar
     * @param p Individuo a representar
     */
    public PedestrianView(Comuna comuna, Pedestrian p) {
        person = p;
        view = new Rectangle(SIZE, SIZE, Color.BLUE);
        view.setX(person.getX() - SIZE / 2);   // Rectangle x position is the X coordinate of the
        // upper-left corner of the rectangle
        view.setY(person.getY() - SIZE / 2); // Rectangle y position is the Y coordinate of the
        // upper-left corner of the rectangle
        comuna.getView().getChildren().addAll(view);
    }
    /**
     * Metodo para actualizar la posicion del circulo y el cuadrado 
     */
    public void update() {
        view.setX(person.getX() - SIZE / 2);
        view.setY(person.getY() - SIZE / 2);
    }
}
