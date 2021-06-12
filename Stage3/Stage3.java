package Stage3;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
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
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Clase principal del programa que se encarga de recibir los parametros de entrada, tal como el archivo con los parametros
 * de la simulacion y el archivo fxml.
 */
public class Stage3 extends Application implements Initializable{
    /**
     * String para guardar los parametros de llamada del programa 
     */
    public static String[] arg;
    /**
     * Stage principal de la aplicacion
     */
    public static Stage primary;
    /**
     * Pane donde se ubica la comuna
     */
    public static Pane p;
    /**
     * Pane donde se ubica el grafico
     */
    public static Pane pg;
    /**
     * Slitpane de la ventana principal de la aplicacion
     */
    public static SplitPane sp;
    /**
     * Borderpane donde se ubica la aplicacion
     */
    public static BorderPane bp;
    /**
     * Variable que contiene el simulador
     */
    public static Simulator sim;
    /**
     * Barra de menu de la ventana principal
     */
    public static SimulatorMenuBar m;
    /**
     * Comuna de la simulacion
     */
    public static Comuna comuna;
    /**
     * Grafico para representar el estado de la pandemia
     */
    public static StackedAreaChart<Number,Number> areaChart;
    /**
     * Eje X del grafico
     */
    public static NumberAxis xAxis;
    /**
     * Eje Y del grafico
     */
    public static NumberAxis yAxis;
    /**
     * Serie para almacenar la cantidad de susceptibles en el tiempo
     */
    public static XYChart.Series<Number,Number> susceptibles;
    /**
     * Serie para almacenar la cantidad de infectados en el tiempo
     */
    public static XYChart.Series<Number,Number> infectados;
    /**
     * Serie para almacenar la cantidad de recuperados en el tiempo
     */
    public static XYChart.Series<Number,Number> recuperados;
    /**
     * Valores del Spinner de N
     */
    private SpinnerValueFactory<Integer> svfn;
    /**
     * Valores del Spinner de I
     */
    private SpinnerValueFactory<Integer> svfi;
    /**
     * Valores del Spinner del I_TIME
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
    private Slider mSlider;
    @FXML
    private TextField p0;
    @FXML
    private TextField p1;
    @FXML
    private TextField p2;
    
    /**
     * Metodo que inicia la aplicacion
     * @param primaryStage EL stage principal del programa
     * @throws Exception En el caso de que suceda una excepcion durante la inicializacion del programa
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        primary = primaryStage;
        Parameters param = getParameters();
        List<String> rawParam = param.getRaw();
        if (rawParam.size() != 1){
            System.out.println("Usage: java Stage3 <configurationFile.txt>");
            System.exit(-1);
        }
        primaryStage.setTitle("Pandemic Graphics Simulator");
        bp = new BorderPane();
        primaryStage.setScene(new Scene(bp, 600, 600));
        SimulatorConfig config = new SimulatorConfig(new Scanner(new File(rawParam.get(0))));
        comuna = new Comuna();
        xAxis = new NumberAxis(); //Creamos los ejes del grafico
        yAxis = new NumberAxis();
        xAxis.setLabel("Time"); //Colocamos el nombre del eje X
        areaChart = new StackedAreaChart<Number,Number>(xAxis,yAxis); //Creamos el grafico
        areaChart.setAnimated(false); 
        areaChart.setCreateSymbols(false);
        areaChart.setTitle("Evolucion de la pandemia"); //Titulo del grafico
        susceptibles = new XYChart.Series<Number,Number>(); //Creamos la serie para almacenar los datos de los individuos susceptibles
        infectados = new XYChart.Series<Number,Number>(); //Creamos la serie para almacenar los datos de los individuos infectados
        recuperados = new XYChart.Series<Number,Number>(); //Creamos la serie para almacenar los datos de los individuos recuperados
        susceptibles.setName("Susceptibles"); //Colocamos el nombre de los datos
        infectados.setName("Infectados");
        recuperados.setName("Recuperados");
        sim = new Simulator(10,1,comuna);
        m = new SimulatorMenuBar(sim);
        bp.setTop(m);
        sp = new SplitPane();
        sp.setOrientation(Orientation.VERTICAL);
        bp.setCenter(sp);
        p = new Pane();
        p.getChildren().add(comuna.getView());
        pg = new Pane();
        pg.getChildren().addAll(areaChart);
        areaChart.prefHeightProperty().bind(pg.heightProperty()); //Unimos el alto del grafico con el del pane que lo contiene
        areaChart.prefWidthProperty().bind(pg.widthProperty()); //Unimos el ancho del grafico con el del pane que lo contiene
        sp.getItems().addAll(p,pg);
        primaryStage.show();
    }
    /**
     * Este metodo se utiliza para inicializar los valores de los campos de la ventana settings, es llamado de forma automatica debido a la implementacion de la interfaz initializable
     * @param url Representa una direccion.
     * @param rb Estos contienen objetos locales especificos para el programa en cuestion, en caso de necesitarlos.
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
        mSlider.setValue(SimulatorConfig.M);
        p0.setText(String.valueOf(SimulatorConfig.P0));
        p1.setText(String.valueOf(SimulatorConfig.P1));
        p2.setText(String.valueOf(SimulatorConfig.P2));
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
            SimulatorConfig.M = mSlider.getValue();
            SimulatorConfig.P0 = Double.valueOf(p0.getText());
            SimulatorConfig.P1 = Double.valueOf(p1.getText());
            SimulatorConfig.P2 = Double.valueOf(p2.getText());
        }catch(Exception e){}
    }

    /**
    * Metodo que se encarga de reiniciar la aplicacion.
    */
    public static void restart(){
        comuna = new Comuna(); //Creamos una nueva comuna 
        sim = new Simulator(10,1,comuna); //Creamos un nuevo simulador 
        xAxis = new NumberAxis(); //Creamos un nuevo grafico 
        yAxis = new NumberAxis();
        xAxis.setLabel("Time");
        areaChart = new StackedAreaChart<Number,Number>(xAxis,yAxis);
        areaChart.setAnimated(false);
        areaChart.setCreateSymbols(false);
        areaChart.setTitle("Evolucion de la pandemia");
        susceptibles = new XYChart.Series<Number,Number>(); //Creamos nuevas series
        infectados = new XYChart.Series<Number,Number>();
        recuperados = new XYChart.Series<Number,Number>();
        susceptibles.setName("Susceptibles");
        infectados.setName("Infectados");
        recuperados.setName("Recuperados");
        bp = new BorderPane();
        primary.setScene(new Scene(bp, 600, 600));
        sp = new SplitPane();
        sp.setOrientation(Orientation.VERTICAL);
        bp.setCenter(sp);
        p = new Pane();
        m = new SimulatorMenuBar(sim);
        bp.setTop(m);
        sp.setOrientation(Orientation.VERTICAL);
        bp.setCenter(sp);
        p.getChildren().add(comuna.getView());
        pg = new Pane();
        pg.getChildren().addAll(areaChart);
        areaChart.prefHeightProperty().bind(pg.heightProperty());
        areaChart.prefWidthProperty().bind(pg.widthProperty());
        sp.getItems().addAll(p,pg);
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
