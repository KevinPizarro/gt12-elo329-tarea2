package Stage3;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;


/**
 * Clase para proveer una barra de menú al usuario y cargar el archivo fxml de settings
 */
public class SimulatorMenuBar extends MenuBar {

    /**
     * Corresponde a dónde se abrirá la ventana de settings
     */
    private Parent root;

    /**
     * Constructor de la clase con 1 parámetro
     * @param simulator Corresponde al simulador
     */
    SimulatorMenuBar (Simulator simulator){
        /**
         * Creación de menú de control
         */
        Menu controlMenu = new Menu("Control");
        
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74
75
76
77
78
79
80
81
82
83
84
85
86
87
88
89
90
91
package Stage2;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
/**
 * Clase para proveer una barra de menú al usuario y cargar el archivo fxml de settings
 */
public class SimulatorMenuBar extends MenuBar {
    /**
     * Corresponde a dónde se abrirá la ventana de settings
     */
    private Parent root;
    /**
     * Constructor de la clase con 1 parámetro
     * @param simulator Corresponde al simulador
     */
    SimulatorMenuBar (Simulator simulator){
        /**
         * Creación de menú de control
         */
        Menu controlMenu = new Menu("Control");
        /**
         * Creación de menú settings
         */
        Menu settingsMenu = new Menu("Settings");
        getMenus().add(controlMenu);
        getMenus().add(settingsMenu);
        MenuItem start = new MenuItem("Start");
        MenuItem stop = new MenuItem("Stop");
        controlMenu.getItems().addAll(start,stop);
        start.setOnAction(e -> simulator.start());
        stop.setOnAction(e -> simulator.stop());
        MenuItem dummy = new MenuItem("");

        /**
         * Crea el item dummy
         */
        settingsMenu.getItems().add(dummy);
        Stage secondaryStage = new Stage();

         /**
         * Manejo de errores, de ser posible cargará el fxml para settings
         */
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("settings.fxml"));
            root = loader.load();
            secondaryStage.setScene(new Scene(root,380,300));
            secondaryStage.setTitle("Settings");

        }catch(IOException e){
            System.out.println("No se pudo encontrar el archivo.fxml");
        }
        settingsMenu.addEventHandler(Menu.ON_SHOWN, e -> settingsMenu.hide());//Si se  muestra el menú principal se esconde settings
        settingsMenu.addEventHandler(Menu.ON_SHOWING, e -> settings(secondaryStage));//Si se  muestra el menú settings se esconde el menú principal
        
    }


    /**
     * Método para abrir la ventana de settings y cerrar la principal
     * @param secondaryStage Corresponde a la ventana de settings
     */
    public void settings(Stage secondaryStage){
        if(SimulatorConfig.stopflag){
            secondaryStage.show();
            Stage3.primary.close();
            secondaryStage.setAlwaysOnTop(true);
        }
        secondaryStage.setOnCloseRequest(e -> abrirmain());
    }

    /**
     * Método para abrir la ventana principal
     */
    public void abrirmain(){
        Stage3.primary.show();
    }
}
