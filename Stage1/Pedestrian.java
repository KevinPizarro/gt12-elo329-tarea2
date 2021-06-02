package Stage1;

import javafx.scene.Group;

public class Pedestrian {
    private double x, y, speed, angle, deltaAngle;
    private double x_tPlusDelta, y_tPlusDelta;
    private Stage1.Comuna comuna;
    private Group pedestrianView;

    public Pedestrian(Stage1.Comuna comuna, double speed, double deltaAngle){
        this.comuna = comuna;
        this.speed = speed;
        this.deltaAngle=deltaAngle;
        x = Math.random()*comuna.getWidth();
        y = Math.random()*comuna.getHeight();
        angle = Math.random()*2*Math.PI;
        pedestrianView = comuna.getView();
    }
    public double getX(){
        return x;
    }
    public double getY() {
        return y;
    }
    public void computeNextState(double delta_t) {
        double r=Math.random();
        angle+=deltaAngle*(1-2*r);
        x_tPlusDelta=x+speed*Math.cos(angle)*delta_t;
        y_tPlusDelta=y+speed*Math.sin(angle)*delta_t;
        if(x_tPlusDelta < 0){   // rebound logic
            x_tPlusDelta=-x_tPlusDelta;
            angle=Math.PI-angle;
        }
        if(y_tPlusDelta < 0){
            y_tPlusDelta=-y_tPlusDelta;
            angle=2*Math.PI-angle;
        }
        if( x_tPlusDelta > comuna.getWidth()){
            x_tPlusDelta=2*comuna.getWidth()-x_tPlusDelta;
            angle=Math.PI-angle;
        }
        if(y_tPlusDelta > comuna.getHeight()){
            y_tPlusDelta=2*comuna.getHeight()-y_tPlusDelta;
            angle=2*Math.PI-angle;
        }
    }
    public void updateState(){
        x=x_tPlusDelta;
        y=y_tPlusDelta;
    }
    public void updateView() {
        Stage1.
    }
}
