package Stage4;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PedestrianView {
    private Pedestrian person;
    private Rectangle view;
    private Circle viewc;
    private Polygon viewt;
    private final double SIZE = 5;

    public PedestrianView(Comuna comuna, Pedestrian p) {
        person = p;
        view = new Rectangle(SIZE, SIZE, Color.BLUE);
        view.setX(person.getX() - SIZE / 2);   // Rectangle x position is the X coordinate of the
        // upper-left corner of the rectangle
        view.setY(person.getY() - SIZE / 2); // Rectangle y position is the Y coordinate of the
        // upper-left corner of the rectangle
        viewc = new Circle(view.getX() + SIZE/2 ,view.getY() + SIZE/2,SIZE/2,Color.rgb(255,0,0,0.0));
        viewt = new Polygon();
        viewt.getPoints().addAll(new Double[]{
            view.getX()+SIZE/2, view.getY(),
            view.getX(), view.getY()+ SIZE,
            view.getX()+SIZE, view.getY()+ SIZE});
        viewt.setFill(Color.rgb(59,179,0,0.0));
        comuna.getView().getChildren().addAll(view);
        comuna.getView().getChildren().addAll(viewc);
        comuna.getView().getChildren().addAll(viewt);
        if(p.getmask()){
            maskBorder(p);
        }
    }
    public void update() {
        view.setX(person.getX() - SIZE / 2);
        view.setY(person.getY() - SIZE / 2);
        viewc.setCenterX(view.getX()+SIZE/2);
        viewc.setCenterY(view.getY()+SIZE/2);
        viewt.getPoints().removeAll(viewt.getPoints());
        viewt.getPoints().addAll(new Double[]{
            view.getX()+SIZE/2, view.getY(),
            view.getX(), view.getY()+ SIZE,
            view.getX()+SIZE, view.getY()+ SIZE});

    }
    public void recColor(Pedestrian p){
        view.setFill(Color.BROWN);
        viewc.setFill(Color.rgb(0, 0, 0, 0.0));
        view.setStroke(Color.rgb(0, 0, 0, 0));
        viewc.setStroke(Color.rgb(0, 0, 0, 0));
        if(p.getmask()){
            maskBorder(p);
        }
    }
    public void infectedColor(Pedestrian p, double y){
        viewc.setFill(Color.rgb(255,0,0,y));
        viewc.setStroke(Color.rgb(255, 0, 0, 1));
        view.setFill(Color.rgb(0, 0, 0, 0.0));
        view.setStroke(Color.rgb(0, 0, 0, 0));
        if(p.getmask()){
            maskBorder(p);
        }
    }
    public void vaccinedColor(Pedestrian p){
        viewt.setFill(Color.rgb(59,179,0,1));
        view.setFill(Color.rgb(0, 0, 0, 0.0));
        view.setStroke(Color.rgb(0, 0, 0, 0));
        viewc.setFill(Color.rgb(0, 0, 0, 0.0));
        viewc.setStroke(Color.rgb(0, 0, 0, 0));
        if(p.getmask()){
            maskBorder(p);
        }
    }
    public void maskBorder(Pedestrian p){
        switch(p.getState()){
            case S:
                view.setStroke(Color.BLACK);
                viewc.setStroke(Color.rgb(0, 0, 0, 0));
                viewt.setStroke(Color.rgb(0, 0, 0, 0));
                break;
            case I:
                viewc.setStroke(Color.BLACK);
                view.setStroke(Color.rgb(0, 0, 0, 0));
                viewt.setStroke(Color.rgb(0, 0, 0, 0));
                break;
            case R:
                view.setStroke(Color.BLACK);
                viewc.setStroke(Color.rgb(0, 0, 0, 0));
                viewt.setStroke(Color.rgb(0, 0, 0, 0));
                break;
            case V:
                viewt.setStroke(Color.BLACK);
                viewc.setStroke(Color.rgb(0, 0, 0, 0));
                view.setStroke(Color.rgb(0, 0, 0, 0));
                break;
        }
        
    }
}
