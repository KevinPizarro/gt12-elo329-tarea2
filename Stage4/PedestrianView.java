package Stage4;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


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
     * Representacion con forma de circulo
     */
    private Circle viewc;
    /**
     * Representacion con forma de poligono triangulo
     */
    private Polygon viewt;
    /**
     * Tamano de la representacion
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
        viewc = new Circle(view.getX() + SIZE/2 ,view.getY() + SIZE/2,SIZE/2,Color.rgb(255,0,0,0.0));//Creamos el circulo de color transparente centrandolo en el cuadrado anterior
        viewt = new Polygon();//Creamos un poligono triangulo de color transparente centrandolo en el cuadrado anterior
        viewt.getPoints().addAll(new Double[]{
            view.getX()+SIZE/2, view.getY(),
            view.getX(), view.getY()+ SIZE,
            view.getX()+SIZE, view.getY()+ SIZE});
        viewt.setFill(Color.rgb(59,179,0,0.0));
        comuna.getView().getChildren().addAll(view); //Agregamos el cuadrado a la representacion de la comuna
        comuna.getView().getChildren().addAll(viewc); //Agregamos el circulo a la representacion de la comuna
        comuna.getView().getChildren().addAll(viewt); //Agregamos el poligono traingulo a la representacion de la comuna
        if(p.getmask()){ //Se muestra graficamente si el individuo tiene mascarilla o no
            maskBorder(p);
        }
    }

    /**
     * Metodo para actualizar la posicion del circulo y el cuadrado 
     */
    public void update() {
        view.setX(person.getX() - SIZE / 2); //Actualizamos la posicion en X del cuadrado 
        view.setY(person.getY() - SIZE / 2); //Actualizamos la posicion en Y del cuadrado 
        viewc.setCenterX(view.getX()+SIZE/2); //Actualizamos la posicion en X del circulo
        viewc.setCenterY(view.getY()+SIZE/2); //Actualizamos la posicion en Y del circulo
        viewt.getPoints().removeAll(viewt.getPoints()); //borramos el poligono triangulo anterior para no que no cambie de forma
        viewt.getPoints().addAll(new Double[]{ //Creamos un nuevo poligono triangulo con las posciones X e Y actualizadas
            view.getX()+SIZE/2, view.getY(),
            view.getX(), view.getY()+ SIZE,
            view.getX()+SIZE, view.getY()+ SIZE});

    }

    /**
     * Metodo para cambiar la representacion del individuo a la de recuperado
     * @param p Individuo al que cambiar 
     */
    public void recColor(Pedestrian p){
        view.setFill(Color.BROWN); //Cambiamos el cuadrado a color cafe
        viewc.setFill(Color.rgb(0, 0, 0, 0.0)); //Cambiamos el circulo a transparente 
        view.setStroke(Color.rgb(0, 0, 0, 0)); //Colocamos los bordes del cuadrado a transparente 
        viewc.setStroke(Color.rgb(0, 0, 0, 0)); //Colocamos los bordes del circulo a transparente 
        if(p.getmask()){ //Se muestra graficamente si el individuo tiene mascarilla o no
            maskBorder(p);
        }
    }

    /**
     * Metodo para cambiar la representacion del individuo a la de infectado
     * @param p Individuo al que cambiar 
     * @param y Valor del alfa del color, para ir cambiando la intensidad del color rojo
     */ 
    public void infectedColor(Pedestrian p, double y){
        viewc.setFill(Color.rgb(255,0,0,y)); //Colocamos el circulo rojo con el alfa definido por el y
        viewc.setStroke(Color.rgb(255, 0, 0, 1)); //Colocamos el borde del circulo rojo intenso
        view.setFill(Color.rgb(0, 0, 0, 0.0)); //Colocamos el cuadrado transparente
        view.setStroke(Color.rgb(0, 0, 0, 0)); //Colocamos el borde del cuadrado transparente
        if(p.getmask()){ //Se muestra graficamente si el individuo tiene mascarilla o no
            maskBorder(p);
        }
    }

    /**
     * Metodo para cambiar la representacion del individuo a la de vacunado
     * @param p Individuo al que cambiar 
     */ 
    public void vaccinedColor(Pedestrian p){
        viewt.setFill(Color.rgb(59,179,0,1)); //Colocamos el triangulo verde
        view.setFill(Color.rgb(0, 0, 0, 0.0)); //hacemos el cuadrado transparente
        view.setStroke(Color.rgb(0, 0, 0, 0));  //hacemos el borde del cuadrado transparente
        viewc.setFill(Color.rgb(0, 0, 0, 0.0)); //hacemos el circulo transparente
        viewc.setStroke(Color.rgb(0, 0, 0, 0)); //hacemos el borde del circulo transparente
        if(p.getmask()){ //Se muestra graficamente si el individuo tiene mascarilla o no
            maskBorder(p);
        }
    }

    /**
     * Metodo para mostrar graficamente si el individuo porta o no mascarilla
     * @param p Individuo al que cambiar 
     */
    public void maskBorder(Pedestrian p){
        switch(p.getState()){ 
            case S: //si es susceptible el borde del cuadrado sera negro y de las otras figuras transparentes
                view.setStroke(Color.BLACK);
                viewc.setStroke(Color.rgb(0, 0, 0, 0));
                viewt.setStroke(Color.rgb(0, 0, 0, 0));
                break;
            case I: //si es infectado el borde del circulo sera negro y de las otras figuras transparentes
                viewc.setStroke(Color.BLACK);
                view.setStroke(Color.rgb(0, 0, 0, 0));
                viewt.setStroke(Color.rgb(0, 0, 0, 0));
                break;
            case R: //si es recuperado el borde del cuadrado sera negro y de las otras figuras transparentes
                view.setStroke(Color.BLACK);
                viewc.setStroke(Color.rgb(0, 0, 0, 0));
                viewt.setStroke(Color.rgb(0, 0, 0, 0));
                break;
            case V: //si es vacunado el borde del poligono triangulo sera negro y de las otras figuras transparentes
                viewt.setStroke(Color.BLACK);
                viewc.setStroke(Color.rgb(0, 0, 0, 0));
                view.setStroke(Color.rgb(0, 0, 0, 0));
                break;
        }
        
    }
}
