package Stage2;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * Clase de la representación gráfica de los individos, permite representar a los individuos en la comuna, y manipular dicha representación
 */
public class PedestrianView {
    /**
     * Individuo a representar
     */
    private Pedestrian person;
    /**
     * Representación con forma de cuadrado
     */
    private Rectangle view;
    /**
     * Representación con forma de circulo
     */
    private Circle viewc;
    /**
     * Tamaño de la representación
     */
    private final double SIZE = 5;

    /**
     * Constructor de PedestrianView. Inicializa una nueva representación
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
        viewc = new Circle(view.getX()+SIZE/2,view.getY()+SIZE/2,SIZE/2,Color.rgb(255,0,0,0.0)); //Creamos el circulo de color transparente centrandolo en el cuadrado anterior
        comuna.getView().getChildren().addAll(view); //Agregamos el cuadrado a la representación de la comuna 
        comuna.getView().getChildren().addAll(viewc); //Agregamos el circulo a la representación de la comuna 
    }
    /**
     * Método para actualizar la posición del circulo y el cuadrado 
     */
    public void update() {
        view.setX(person.getX() - SIZE / 2); //Actualizamos la posición en X del cuadrado 
        view.setY(person.getY() - SIZE / 2); //Actualizamos la posición en Y del cuadrado 
        viewc.setCenterX(view.getX()+SIZE/2); //Actualizamos la posición en X del circulo
        viewc.setCenterY(view.getY()+SIZE/2); //Actualizamos la posición en Y del circulo 
    }
    /**
     * Método para cambiar la representación del individuo a la de recuperado
     * @param p Individuo al que cambiar 
     */
    public void recColor(Pedestrian p){
        view.setFill(Color.BROWN); //Cambiamos el cuadrado a color café
        viewc.setFill(Color.rgb(0, 0, 0, 0.0)); //Cambiamos el circulo a transparente 
        view.setStroke(Color.rgb(0, 0, 0, 0)); //Colocamos los bordes del cuadrado a transparente 
        viewc.setStroke(Color.rgb(0, 0, 0, 0)); //Colocamos los bordes del circulo a transparente 
    }
    /**
     * Método para cambiar la representación del individuo a la de infectado
     * @param p Individuo al que cambiar 
     * @param y Valor del alfa del color, para ir cambiando la intensidad del color rojo
     */
    public void infectedColor(Pedestrian p, double y){
        viewc.setFill(Color.rgb(255,0,0,y)); //Colocamos el circulo rojo con el alfa definido por el y
        viewc.setStroke(Color.rgb(255, 0, 0, 1)); //Colocamos el borde del circulo rojo intenso
        view.setFill(Color.rgb(0, 0, 0, 0.0)); //Colocamos el cuadrado transparente
        view.setStroke(Color.rgb(0, 0, 0, 0)); //Colocamos el borde del cuadrado transparente
    }
}
