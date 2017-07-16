/**
 * Created by zhangjing60 on 17/7/16.
 */
public class Main {
    public static void main(String[] args) {
        for(double lamda = 200; lamda <= 300; lamda += 10) {
            SimulationImpl simulation = new SimulationImpl();
            simulation.initialize(lamda);
            simulation.run();
        }
    }
}
