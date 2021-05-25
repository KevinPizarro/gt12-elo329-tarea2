package Stage1;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class SimulatorMenuBar extends MenuBar {
    SimulatorMenuBar (Simulator simulator){
        Menu controlMenu = new Menu("Control");
        getMenus().add(controlMenu);
        MenuItem start = new MenuItem("Start");
        controlMenu.getItems().addAll(start,???);
        start.setOnAction(?????);
        ????????????
    }
}
