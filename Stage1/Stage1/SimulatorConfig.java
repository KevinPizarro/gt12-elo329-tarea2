package Stage2;
import java.util.Scanner;

/**
 * Clase que se encarga de almacenar los datos extraidos del archivo dado en la entrada.
 */
public class SimulatorConfig {
    /**
     * Numero de individuos
     */
    public static int N;
    /**
     * Numero de individuos infectados
     */
    public static int I;
    /**
     * Tiempo de recuperación de los individuos
     */
    public static double I_TIME;
    /**
     * Ancho de la comuna
     */
    public static double WIDTH;
    /**
     * Alto de la comuna
     */
    public static double LENGTH;
    /**
     * Velocidad de movimiento
     */
    public static double SPEED;
    /**
     * Variación del paso de tiempo
     */
    public static double DELTA_T;
    /**
     * Variación máxima del ángulo de movimiento
     */
    public static double DELTA_THETA;
    /**
     * Distancia máxima de contagio
     */
    public static double D;
    /**
     * Proporción de los individuos con mascarilla
     */
    public static double M;
    /**
     * Probabilidad de contagio ambos individuos sin mascarilla
     */
    public static double P0;
    /**
     * Probabilidad de contagio un individuo sin mascarilla
     */
    public static double P1;
    /**
     * Probabilidad de contagio de ambos individuos con mascarilla
     */
    public static double P2;
    /**
     * Cantidad de vacunatorios a colocar en la comuna
     */
    public static int NUM_VAC;
    /**
     * Tamaño del lateral de los vacunatorios
     */
    public static int VAC_SIZE;
    /**
     * Tiempo para que aparezcan los vacunatorios
     */
    public static int VAC_TIME;

    /**
     * Constructor de la clase SimulatorConfig.
     * @param s Scanner donde se encuentran los datos de los parámetros
     */
    public SimulatorConfig(Scanner s){
        N=s.nextInt(); I= s.nextInt(); I_TIME=s.nextDouble();
        WIDTH=s.nextDouble(); LENGTH= s.nextDouble();
        SPEED=s.nextDouble(); DELTA_T=s.nextDouble(); DELTA_THETA=s.nextDouble();
        D=s.nextDouble(); M=s.nextDouble(); P0=s.nextDouble(); P1=s.nextDouble(); P2=s.nextDouble();
        NUM_VAC=s.nextInt(); VAC_SIZE=s.nextInt(); VAC_TIME=s.nextInt();
    }
}
