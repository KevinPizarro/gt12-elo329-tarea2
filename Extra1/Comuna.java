package Extra1;
import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * Clase que maneja la comuna donde se desarrolla la simulacion
 */
public class Comuna {
    /**
     * Arreglo de individuos
     */
    private ArrayList<Pedestrian> personas;
    /**
     * Arreglo de vacunatorios
     */
    private ArrayList<Rectangle> vacunatorios;
    /**
     * Territorio de la comuna
     */
    private Rectangle2D territory; // Alternatively: double width, length;
                                   // but more methods would be needed.
    /**
     * Representacion visual de la comuna
     */
    private ComunaView view;
    /**
     * Variable para confirmar la creacion de los vacunatorios
     */
    private boolean vacFlag; 
    /**
     * Constructor de la clase comuna
     */
    public Comuna(){
        double width = SimulatorConfig.WIDTH;
        double length = SimulatorConfig.LENGTH;
        territory = new Rectangle2D(0,0, width, length);
        double speed = SimulatorConfig.SPEED;
        double deltaAngle = SimulatorConfig.DELTA_THETA;
        view = new ComunaView(this); 
        personas = new ArrayList<Pedestrian>(SimulatorConfig.N);
        vacFlag = true; //Seteamos en true para indicar que no se han colocado los vacunatorios
        for(int i=0;i<SimulatorConfig.N;i++){ //Creamos a los individuos de la comuna
            if(i<SimulatorConfig.I){ //Creamos la cantidad indicada de individuos infectados
                Pedestrian p=new Pedestrian(this, speed, deltaAngle);
                personas.add(p);
                personas.get(i).infect();
                if(i < SimulatorConfig.I*SimulatorConfig.M){ //Creamos la cantidad indicada de individuos infectados con mascarilla
                    personas.get(i).putmask();
                }
            }
            else{ //Creamos los individuos susceptibles
                Pedestrian p=new Pedestrian(this, speed, deltaAngle);
                personas.add(p);
                if(i-SimulatorConfig.I < (SimulatorConfig.N-SimulatorConfig.I)*SimulatorConfig.M){  //Creamos la cantidad indicada de individuos susceptibles con mascarilla
                    personas.get(i).putmask();
                }
            }
        }
    }
    /**
     * Metodo para obtener el ancho de la comuna
     * @return Ancho de la comuna como double 
     */
    public double getWidth() {
        return territory.getWidth();
    }
    /**
     * Metodo para obtener el alto de la comuna
     * @return Alto de la comuna como double 
     */
    public double getHeight() {
        return territory.getHeight();
    }
    /**
     * Revisa el estado de los individuos y los actualiza, calculando cuando un individuo se contagia
     * @param delta_t Variacion de tiempo con el que se calcula el siguiente estado
     * @param simt Tiempo actual de la simulacion
     */
    public void computeNextState (double delta_t, double simt) {
        if(SimulatorConfig.VAC_TIME <= simt && vacFlag){ //Si no se han colocado los vacunatorios, y llega el tiempo de colocarlos
            setvac(); //Colocamos los vacunatorios
            for(int i = 0; i < SimulatorConfig.NUM_VAC; i++){ //Agregamos los vacunatorios a la representacion grafica
                getView().getChildren().add(vacunatorios.get(i));
            }
            vacFlag = false; //Seteamos en false para no volver a colocar los vacunatorios en esta simulacion
        }
        for(int i=0;i < personas.size();i++){ //Calculamos que sucede con cada individuo
            for(int j=0; j< personas.size();j++){
                if(simt >= SimulatorConfig.VAC_TIME){ //Si ya empezaron a vacunar
                    if(personas.get(i).getState() == State.S && existvac(personas.get(i).getX(), personas.get(i).getY())){ 
                        //Revisamos si el individuo es apto para vacunarse, y si se encuentra en un vacunatorio
                        personas.get(i).vaccine(); //Vacunamos al individuo
                    }                    
                }
                if(personas.get(i).getState()==State.S && personas.get(j).getState()==State.I){ //Calculamos si debemos infectar al individuo
                    double e = Math.sqrt(Math.pow(personas.get(i).getX()-personas.get(j).getX(),2)+Math.pow(personas.get(i).getY()-personas.get(j).getY(),2)); //Calculamos la distancia entre los individuos a revisar
                    if(e < SimulatorConfig.D){ //Si dicha distancia es menor a d
                        if((personas.get(i).getmask() == true) && (personas.get(j).getmask() == true)){ //Verificamos si ambos ocupan mascarilla
                            if(Math.random() <= SimulatorConfig.P2){
                                personas.get(i).infect();
                            }
                        }
                        else if(((personas.get(i).getmask() == true) && (personas.get(j).getmask() == false)) || ((personas.get(i).getmask() == false) && (personas.get(j).getmask() == true))){ 
                            //Verificamos si solo uno ocupa mascarilla
                            if(Math.random() <= SimulatorConfig.P1){
                                personas.get(i).infect();
                            }
                        } 
                        else{ //Si ninguno tiene mascarilla
                            if(Math.random() <= SimulatorConfig.P0){
                                personas.get(i).infect();
                            }
                        }
                    }
                }
            }
        }
        for(int i=0; i<personas.size();i++) { //Calculamos el siguiente estado de cada individuo
            personas.get(i).computeNextState(delta_t);
        }
    }
    /**
     * Metodo que actualiza el estado de cada idividuo
     */
    public void updateState () {
        for(int i=0; i<personas.size();i++){
            personas.get(i).updateState();
        }
    }
    /**
     * Metodo que actualiza la representacion grafica de la comuna y todo en su interior
     */
    public void updateView(){
        view.update();
    }
    /**
     * Metodo que entrega el arreglo de personas pertenecientes a la comuna 
     * @return ArrayList de los individuos de la comuna
     */
    public ArrayList<Pedestrian> getPedestrian() {
        return personas;
    }
    /**
     * Metodo para obtener la representacion grafica de la comuna
     * @return Group que contiene la representacion grafica de la comuna
     */
    public Group getView() {
        return view;
    }
    /**
     * Metodo para obtener la cantidad de individuos susceptibles en la simulacion
     * @return int con la cantidad de individuos susceptibles
     */
    public int getSus(){
        int sus=0;
        State status;
        for(int i=0; i<personas.size();i++){
            status=personas.get(i).getState();
            if(status == State.S){
                sus++;
            }
        }
        return sus;
    }
    /**
     * Metodo para obtener la cantidad de individuos infectados en la simulacion
     * @return int con la cantidad de individuos infectados
     */
    public int getInf(){
        int inf=0;
        State status;
        for(int i=0; i<personas.size();i++){
            status=personas.get(i).getState();
            if(status == State.I){
                inf++;
            }
        }
        return inf;
    }
    /**
     * Metodo para obtener la cantidad de individuos recuperados en la simulacion
     * @return int con la cantidad de individuos recuperados
     */
    public int getRec(){
        int rec=0;
        State status;
        for(int i=0; i<personas.size();i++){
            status=personas.get(i).getState();
            if(status == State.R){
                rec++;
            }
        }
        return rec;
    }
    /**
     * Metodo para obtener la cantidad de individuos vacunados en la simulacion
     * @return int con la cantidad de individuos vacunados
     */
    public int getVac(){
        int vac = 0;
        State status;
        for(int i=0; i<personas.size();i++){
            status=personas.get(i).getState();
            if(status == State.V){
                vac++;
            }
        }
        return vac;
    }
    /**
     * Metodo que coloca los vacunatorios en la comuna
     */
    public void setvac(){
        double x; //Posicion en X del vacunatorio a crear
        double y; //Posicion en Y del vacunatorio a crear
        boolean flag = false; //flag para evitar quedarse atorado en el while en el caso de que la distribucion aleatoria no permita ubicar los vacunatorios
        int cont = 0; //Contador para evitar quedarse atorado en el while en el caso de que la distribucion aleatoria no permita ubicar los vacunatorios
        Rectangle vac; //Vacunatorio a crear
        Rectangle vac1; //Primer vacunatorio a crear
        do{ 
            vacunatorios = new ArrayList<Rectangle>(SimulatorConfig.NUM_VAC);
            cont = 0;
            flag = false;
            for(int i = 0; i < SimulatorConfig.NUM_VAC; i++){
                x = Math.random()*territory.getWidth();
                y = Math.random()*territory.getHeight();
                if(x >= territory.getWidth() - SimulatorConfig.VAC_SIZE){ //Nos aseguramos que el vacunatorio quede dentro de la comuna en X
                    x -= SimulatorConfig.VAC_SIZE;
                }
                if(y >= territory.getHeight() - SimulatorConfig.VAC_SIZE){ //Nos aseguramos que el vacunatorio quede dentro de la comuna en Y
                    y -= SimulatorConfig.VAC_SIZE;
                }
                if(i == 0){ //Si es el primer vacunatorio, lo colocamos en cualquier parte interior de la comuna
                    vac1 = new Rectangle(x, y, SimulatorConfig.VAC_SIZE, SimulatorConfig.VAC_SIZE);
                    vac1.setFill(Color.rgb(149, 255, 128, 0.5));
                    vacunatorios.add(vac1);
                }
                else{ //Para todos los vacunatorios menos el primero
                    vac = new Rectangle(x,y,SimulatorConfig.VAC_SIZE,SimulatorConfig.VAC_SIZE);
                    if(intervac(vac)){ //Revisamos si el vacunatorio intercepta con alguno de los vacunatorios ya colocados 
                        while(intervac(vac)){ //Hasta que conseguir una posicion que no intercepte ningun vacunatorio anterior, recalculamos X e Y
                            x = Math.random()*territory.getWidth();
                            y = Math.random()*territory.getHeight();
                            if(x >= territory.getWidth() - SimulatorConfig.VAC_SIZE){
                                x -= SimulatorConfig.VAC_SIZE;
                            }
                            if(y >= territory.getHeight() - SimulatorConfig.VAC_SIZE){
                                y -= SimulatorConfig.VAC_SIZE;
                            }
                            vac = new Rectangle(x, y, SimulatorConfig.VAC_SIZE, SimulatorConfig.VAC_SIZE);
                            cont++; //Contamos cuantas veces se ha intentado ubicar dicho Vacunatorio
                            if (cont > 10){ //Si llevamos mas de 10 intentos
                                flag = true; // Seteamos la flag en true y salimos del for
                                break;
                            }
                        }
                    } //Cuando encontramos un vacunatorio que cumple con las condiciones, lo agregamos al ArrayList
                    vac = new Rectangle(x, y, SimulatorConfig.VAC_SIZE, SimulatorConfig.VAC_SIZE);
                    vac.setFill(Color.rgb(149, 255, 128, 0.5));
                    vacunatorios.add(vac);
                }
            }
        } while (flag); //si la flag se activa, repetimos todo el proceso desde 0
    }
    /** 
     * Metodo para verificar si existe algun vacunatorio en la posicion dada
     * @param x Coordenada X de la posicion
     * @param y Coordenada Y de la posicion
     * @return bolean que indica si existe o no un vacunatorio en la posicion dada
     */
    public boolean existvac(double x, double y){
        for(int i = 0; i < vacunatorios.size(); i++){
            if(vacunatorios.get(i).contains(x,y)){
                return true;
            }
        }
        return false;
    }
    /**
     * Metodo que verifica si el vacunatorio del parametro intersecta alguno de los ya creados en el ArrayList
     * @param nuevo Vacunatorio a verificar
     * @return boolean que indica si existe o no un vacunatorio(o mas) que intersecten con el vacunatorio a verificar
     */
    public boolean intervac(Rectangle nuevo){
        for(int i = 0; i < vacunatorios.size(); i++){
            if(nuevo.intersects(vacunatorios.get(i).getX(),vacunatorios.get(i).getY(),vacunatorios.get(i).getWidth(),vacunatorios.get(i).getHeight())){
                return true;
            }
        }
        return false;
    }

 }
