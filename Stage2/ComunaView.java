package Stage2;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ComunaView extends Group {
    private final Comuna comuna;
    public ComunaView(Comuna c){
        comuna = c;
        Rectangle territoryView = new Rectangle(comuna.getWidth(), comuna.getHeight(), Color.WHITE);
        territoryView.setStroke(Color.BROWN);
        getChildren().add(territoryView);
        setFocusTraversable(true);  // needed to receive mouse and keyboard events.
    }
    public void update(){
        comuna.getPedestrian().updateView();
    }
}
