package Stage4;
import java.util.Scanner;

public class SimulatorConfig {  // This class is just to read the input configuration file
    // and then to have a convenient way to access each parameter.
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
