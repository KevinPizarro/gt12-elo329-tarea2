package Stage1;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;

/**
 * Clase principal del programa que se encarga de recibir los parametros de entrada, tal como el árchivo con los parametros
 * de la simulación y el archivo fxml.
 * Las instancias de la clase primary, p, sp, bp, sim, m, comuna son ocupados al momento de reiniciar la aplicación
 * svfn, svfi y svfitime son ocupados para detectar y guardar los valores detectados en los spinner de la interfaz de "Settings"
 */
public class Stage1 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parameters param = getParameters();
        List<String> rawParam = param.getRaw();
        if (rawParam.size() != 1) {
            System.out.println("Usage: java Stage1 <configurationFile.txt>");
            System.exit(-1);
        }
        primaryStage.setTitle("Pandemic Graphics Simulator");
        BorderPane borderPane = new BorderPane();
        primaryStage.setScene(new Scene(borderPane, 600, 400));
        SimulatorConfig config = new SimulatorConfig(new Scanner(new File(rawParam.get(0))));
        Comuna comuna = new Comuna();
        Simulator simulator = new Simulator(10,1,comuna);
        borderPane.setTop(new SimulatorMenuBar(simulator));
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        borderPane.setCenter(splitPane);
        Pane pane = new Pane();
        pane.getChildren().add(comuna.getView());
        splitPane.getItems().addAll(pane, comuna.getGraph());
        primaryStage.show();
    }

    /**
     *  Main del programa
     * @param args Parametros de entrada.
     */
    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US")); //Para asumir el punto como decimal
        launch(args);
    }
}
