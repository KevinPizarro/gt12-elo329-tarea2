package Stage4;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
/**
 * Clase que permite representar gráficamente la comuna 
 */
public class ComunaView extends Group {
    /**
     * Comuna a representar
     */
    private Comuna comuna;
    /**
     * Constructor de la clase ComunaView
     * @param c Comuna a representar 
     */
    public ComunaView(Comuna c){
        comuna = c;
        Rectangle territoryView = new Rectangle(comuna.getWidth(), comuna.getHeight(), Color.WHITE);
        territoryView.setStroke(Color.BROWN);
        getChildren().add(territoryView);
        setFocusTraversable(true);  // needed to receive mouse and keyboard events.
    }
    /**
     * Método que actualiza visualmente los componentes de la comuna
     */
    public void update(){
        ArrayList<Pedestrian> personas = comuna.getPedestrian();
        for(int i=0; i<personas.size();i++){
            personas.get(i).updateView();
        }
    }
}
