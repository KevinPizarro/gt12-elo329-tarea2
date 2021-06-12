package Stage3;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class ComunaView extends Group {
    /*
     *  Comuna que se esta representando gráficamente
     */
    private Comuna comuna;
    /*
     * Constructor de clase ComunaView
     */
    public ComunaView(Comuna c){
        comuna = c;
        Rectangle territoryView = new Rectangle(comuna.getWidth(), comuna.getHeight(), Color.WHITE);
        territoryView.setStroke(Color.BROWN);
        getChildren().add(territoryView);
        setFocusTraversable(true);  // needed to receive mouse and keyboard events.
    }
    /*
     * Actualiza visualmente los componentes adentro de comuna
     */
    public void update(){
        ArrayList<Pedestrian> personas = comuna.getPedestrian();
        for(int i=0; i<personas.size();i++){
            personas.get(i).updateView();
        }
    }
}
