package Stage2;
//java --module-path D:\JDK\FX\lib --add-modules javafx.controls,javafx.fxml,javafx.media Stage2.Stage2 Configuracion.txt
//javac --module-path D:\JDK\FX\lib --add-modules javafx.controls,javafx.fxml,javafx.media Stage2\Stage2.java
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase principal del programa que se encarga de recibir los parametros de entrada, tal como el árchivo con los parametros
 * de la simulación y el archivo fxml.
 * Las instancias de la clase primary, p, sp, bp, sim, m, comuna son ocupados al momento de reiniciar la aplicación
 * svfn, svfi y svfitime son ocupados para detectar y guardar los valores detectados en los spinner de la interfaz de "Settings"
 */
public class Stage2 extends Application implements Initializable{
    /**
     * Argumentos pasados al inicio.
     */
    public static String[] arg; //
    /**
     * Stage principal de la aplicación
     */
    public static Stage primary;
    /**
     * Pane encargado de mostrar la comuna
     */
    public static Pane p;
    /**
     * Spliotplane de la ventana
     */
    public static SplitPane sp;
    /**
     * Borderpane de la ventana
     */
    public static BorderPane bp;
    /**
     * Simulación de la comuna
     */
    public static Simulator sim;
    /**
     * Crea los menus de la aplicación
     */
    public static SimulatorMenuBar m;
    /**
     * La comuna
     */
    public static Comuna comuna;
    /**
     * Spinner que lee la cantidad de individuos
     */
    private SpinnerValueFactory<Integer> svfn;
    /**
     * Spinner que lee la cantidad de infectados
     */
    private SpinnerValueFactory<Integer> svfi;
    /**
     * Spinner que lee el tiempo de recuperación luego de ser infectado
     */
    private SpinnerValueFactory<Double> svfitime;

    /**
     * Se definen los metodos implementados en el archivo fxml
     */
    @FXML
    private Spinner<Integer> nSpinner;
    @FXML
    private Spinner<Integer> iSpinner;
    @FXML
    private Spinner<Double> itimeSpinner;
    @FXML
    private TextField width;
    @FXML
    private TextField lenght;
    @FXML
    private TextField speed;
    @FXML
    private TextField delta_t;
    @FXML
    private TextField theta;
    @FXML
    private TextField d;
    @FXML
    private TextField p0;

    /**
     *
     * @param primaryStage EL stage principal del programa
     * @throws Exception En el caso de que suceda una excepción durante la inicialización del programa
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        primary = primaryStage;
        Parameters param = getParameters();
        List<String> rawParam = param.getRaw();
        if (rawParam.size() != 1){
            System.out.println("Usage: java Stage2 <configurationFile.txt>");
            System.exit(-1);
        }
        primaryStage.setTitle("Pandemic Graphics Simulator");
        bp = new BorderPane();
        primaryStage.setScene(new Scene(bp, 600, 400));
        SimulatorConfig config = new SimulatorConfig(new Scanner(new File(rawParam.get(0))));
        comuna = new Comuna();
        sim = new Simulator(10,1,comuna);
        m = new SimulatorMenuBar(sim);
        bp.setTop(m);
        sp = new SplitPane();
        sp.setOrientation(Orientation.VERTICAL);
        bp.setCenter(sp);
        p = new Pane();
        p.getChildren().add(comuna.getView());
        sp.getItems().addAll(p, comuna.getGraph());
        primaryStage.show();
    }

    /**
     *
     * @param url Representa una dirección WEB.
     * @param rb Estos contienen objetos locales especificos para el programa en cuestión, en caso de necesitarlos.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        svfn = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5000,SimulatorConfig.N);
        svfi = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5000,SimulatorConfig.I);
        svfitime = new SpinnerValueFactory.DoubleSpinnerValueFactory(1,10000,SimulatorConfig.I_TIME);
        nSpinner.setValueFactory(svfn);
        iSpinner.setValueFactory(svfi);
        itimeSpinner.setValueFactory(svfitime);
        width.setText(String.valueOf(SimulatorConfig.WIDTH));
        lenght.setText(String.valueOf(SimulatorConfig.LENGTH));
        speed.setText(String.valueOf(SimulatorConfig.SPEED));
        delta_t.setText(String.valueOf(SimulatorConfig.DELTA_T));
        theta.setText(String.valueOf(SimulatorConfig.DELTA_THETA));
        d.setText(String.valueOf(SimulatorConfig.D));
        p0.setText(String.valueOf(SimulatorConfig.P0));
    }

    /**
     * Metodo que guarda los valores obtenidos de la interfaz de "Settings"
     */
    public void guardar(){
        try{
            SimulatorConfig.N = nSpinner.getValue();
            SimulatorConfig.I = iSpinner.getValue();
            SimulatorConfig.I_TIME = itimeSpinner.getValue();
            SimulatorConfig.WIDTH = Double.valueOf(width.getText());
            SimulatorConfig.LENGTH = Double.valueOf(lenght.getText());
            SimulatorConfig.SPEED = Double.valueOf(speed.getText());
            SimulatorConfig.DELTA_T = Double.valueOf(delta_t.getText());
            SimulatorConfig.DELTA_THETA = Double.valueOf(theta.getText());
            SimulatorConfig.D = Double.valueOf(d.getText());
            SimulatorConfig.P0 = Double.valueOf(p0.getText());
        }catch(Exception e){}
    }

    /**
     * Metodo que se encarga de reiniciar la aplicación.
     */
    public static void restart(){
        comuna = new Comuna();
        sim = new Simulator(10,1,comuna);
        bp = new BorderPane();
        primary.setScene(new Scene(bp, 600, 400));
        sp = new SplitPane();
        sp.setOrientation(Orientation.VERTICAL);
        bp.setCenter(sp);
        p = new Pane();
        m = new SimulatorMenuBar(sim);
        bp.setTop(m);
        sp.setOrientation(Orientation.VERTICAL);
        bp.setCenter(sp);
        p.getChildren().add(comuna.getView());
        sp.getItems().addAll(p, comuna.getGraph());
    }

    /**
     *  Main del programa
     * @param args Parametros de entrada.
     */
    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US")); //Para asumir el punto como decimal
        arg = args;
        launch(args);
    }
}
