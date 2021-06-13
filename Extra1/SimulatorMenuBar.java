package Extra1;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * Clase para proveer una barra de menu al usuario y cargar el archivo fxml de settings
 */
public class SimulatorMenuBar extends MenuBar {
    /**
     * Corresponde a donde se abrira la ventana de settings
     */
    private Parent root;

    /**
     * Constructor de la clase
     * @param Corresponde al simulador
     */
    SimulatorMenuBar(Simulator simulator) {
        /**
         * Creacion de menu de control
         */
        Menu controlMenu = new Menu("Control");
        Menu settingsMenu = new Menu("Settings");
        getMenus().add(controlMenu);
        getMenus().add(settingsMenu);
        MenuItem start = new MenuItem("Start");
        MenuItem stop = new MenuItem("Stop");
        controlMenu.getItems().addAll(start, stop);
        start.setOnAction(e -> simulator.start());
        stop.setOnAction(e -> simulator.stop());
        MenuItem dummy = new MenuItem("");
        /**
         * Crea el item dummy
         */
        settingsMenu.getItems().add(dummy);
        Stage secondaryStage = new Stage();
        /**
         * Manejo de errores, de ser posible cargara el fxml para settings
         */
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("settings.fxml"));
            root = loader.load();
            secondaryStage.setScene(new Scene(root, 380, 330));
            secondaryStage.setTitle("Settings");

        } catch (IOException e) {
            System.out.println("No se pudo encontrar el archivo.fxml");
        }
        settingsMenu.addEventHandler(Menu.ON_SHOWN, e -> settingsMenu.hide());//Si se  muestra el menu principal se esconde settings
        settingsMenu.addEventHandler(Menu.ON_SHOWING, e -> settings(secondaryStage));//Si se  muestra el menu settings se esconde el menu principal

    }

    /**
     * Metodo para abrir la ventana de settings y cerrar la principal
     * @param secondaryStage Corresponde a la ventana de settings
     */
    public void settings(Stage secondaryStage) {
        if (SimulatorConfig.stopflag) {
            secondaryStage.show();
            Stage4.primary.close();
            secondaryStage.setAlwaysOnTop(true);
        }
        secondaryStage.setOnCloseRequest(e -> abrirmain());
    }

    /**
     * Metodo para abrir la ventana principal
     */
    public void abrirmain() {
        Stage4.primary.show();
    }
}
