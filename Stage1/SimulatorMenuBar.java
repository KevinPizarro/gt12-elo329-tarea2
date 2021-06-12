package Stage1;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Clase para proveer una barra de menu al usuario y cargar el archivo fxml de settings
 */
public class SimulatorMenuBar extends MenuBar {
    /**
     * Constructor de la clase SimulatorMenuBar con 1 parametro
     * @param simulator Corresponde al simulador
     */
    SimulatorMenuBar (Simulator simulator){
        Menu controlMenu = new Menu("Control");
        getMenus().add(controlMenu);
        MenuItem start = new MenuItem("Start");
        MenuItem stop = new MenuItem("Stop");
        controlMenu.getItems().addAll(start,stop);
        start.setOnAction(e -> simulator.start());
        stop.setOnAction(e -> simulator.stop());
    }
}
