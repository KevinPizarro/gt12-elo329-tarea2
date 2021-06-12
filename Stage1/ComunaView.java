package Stage1;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * Representacion visual de la comuna 
 */
public class ComunaView extends Group {
    /**
     * Comuna que se esta representando graficamente
     */
    private final Comuna comuna;

    /**
     * Constructor de clase ComunaView
     * @param c comuna donde se realizara el analisis
     */
    public ComunaView(Comuna c){
        comuna = c;
        Rectangle territoryView = new Rectangle(comuna.getWidth(), comuna.getHeight(), Color.WHITE);
        territoryView.setStroke(Color.BROWN);
        getChildren().add(territoryView);
        setFocusTraversable(true);  // needed to receive mouse and keyboard events.
    }
    /**
     * Actualiza visualmente los componentes adentro de comuna
     */
    public void update(){
        comuna.getPedestrian().updateView();
    }
}
