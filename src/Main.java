import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangjing60 on 17/7/16.
 */
public class Main {
    //static int MAXIMUM = 20;
    public static void main(String[] args) {
        for(double lamda = 200; lamda <= 300; lamda += 10) {
            SimulationImpl simulation = new SimulationImpl();
            simulation.initialize(lamda);
            simulation.run();

        }
    }
}
