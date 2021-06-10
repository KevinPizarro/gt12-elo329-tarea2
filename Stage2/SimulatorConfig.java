package Stage2;
import java.util.Scanner;

/**
 * Clase que se encarga de almacenar los datos extraidos del archivo dado en la entrada.
 */
public class SimulatorConfig {
    /**
     * N Numero de individuos, I Numero de individuos
     * I_TIME Tiempo de recuperación de los individuos
     * WIDTH, LENGHT; Ancho, largo
     * SPEED: Velocidad, DELTA_T: Variación del tiempo por cada cambio de estado en la simulacion, DELTA_THETA: Rango del angulo al que un individuo podrá moverse
     * D: Distancia  la cual un individuo se puede infectar con otro, M: Probabilidad de que un indiviuo  use mascarilla,
     * P0: Probabilidad de contagio cuando ninguno ocupa máscarilla, P1: Probabilidad de contagio cuando uno ocupa mascarilla
     * P2: Probabilidad de contagio cuando los dos ocupan mascarilla, NUM_VAC: Número de vacunatorios, VAC_SIZE: Tamaño de los vacunatorios,
     * VAC_TIME: Tiempo a la cual se activan los vacunatorios.
     */
    public static int N, I;
    public static double I_TIME;
    public static double WIDTH, LENGTH;
    public static double SPEED, DELTA_T, DELTA_THETA;
    public static double D, M, P0, P1, P2;
    public static int NUM_VAC, VAC_SIZE, VAC_TIME;
    public static boolean stopflag = true;

    public SimulatorConfig(Scanner s){
        N=s.nextInt(); I= s.nextInt(); I_TIME=s.nextDouble();
        WIDTH=s.nextDouble(); LENGTH= s.nextDouble();
        SPEED=s.nextDouble(); DELTA_T=s.nextDouble(); DELTA_THETA=s.nextDouble();
        D=s.nextDouble(); M=s.nextDouble(); P0=s.nextDouble(); P1=s.nextDouble(); P2=s.nextDouble();
        NUM_VAC=s.nextInt(); VAC_SIZE=s.nextInt(); VAC_TIME=s.nextInt();
    }
}
