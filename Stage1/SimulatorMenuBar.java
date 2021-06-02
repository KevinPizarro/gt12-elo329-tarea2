package Stage1;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.awt.event.MouseEvent;

public class SimulatorMenuBar extends MenuBar {
    SimulatorMenuBar (Stage1.Simulator simulator){
        Menu controlMenu = new Menu("Control");
        getMenus().add(controlMenu);
        MenuItem start = new MenuItem("Start");
        MenuItem stop = new MenuItem("Stop");
        controlMenu.getItems().addAll(start,stop);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                simulator.start();
            }
        });
        stop.setOnAction(e -> simulator.stop());


    }
}
