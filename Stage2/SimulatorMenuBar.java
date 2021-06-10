package Stage2;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class SimulatorMenuBar extends MenuBar {
    private Parent root;
    SimulatorMenuBar (Simulator simulator){
        Menu controlMenu = new Menu("Control");
        Menu settingsMenu = new Menu("Settings");
        getMenus().add(controlMenu);
        getMenus().add(settingsMenu);
        MenuItem start = new MenuItem("Start");
        MenuItem stop = new MenuItem("Stop");
        controlMenu.getItems().addAll(start,stop);
        start.setOnAction(e -> simulator.start());
        stop.setOnAction(e -> simulator.stop());
        MenuItem dummy = new MenuItem("");
        settingsMenu.getItems().add(dummy);
        Stage secondaryStage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("settings.fxml"));
            root = loader.load();
            secondaryStage.setScene(new Scene(root,380,180));
            secondaryStage.setTitle("Settings");

        }catch(IOException e){
            System.out.println("No se pudo encontrar el archivo.fxml");
        }
        settingsMenu.addEventHandler(Menu.ON_SHOWN, e -> settingsMenu.hide());
        settingsMenu.addEventHandler(Menu.ON_SHOWING, e -> settings(secondaryStage));
        
    }
    public void settings(Stage secondaryStage){
        if(SimulatorConfig.stopflag){
            secondaryStage.show();
            Stage2.primary.close();
            secondaryStage.setAlwaysOnTop(true);
        }
        secondaryStage.setOnCloseRequest(e -> abrirmain());
    }
    public void abrirmain(){
        Stage2.primary.show();
    }
}
