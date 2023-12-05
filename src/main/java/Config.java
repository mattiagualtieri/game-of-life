import enums.SimulationMode;

import java.io.IOException;
import java.util.Properties;

public class Config {

    static int width;
    static int height;
    static int rows;
    static int columns;
    static double alivePercentage;
    static long tick;
    static int logDensityPeriod;
    static SimulationMode mode;
    static int birthNeighboursCount;
    static int overcrowdingNeighboursCount;
    static int lonelinessNeighboursCount;

    public static void load() {
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("simulation.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        width = Integer.parseInt(properties.getProperty("width"));
        height = Integer.parseInt(properties.getProperty("height"));
        rows = Integer.parseInt(properties.getProperty("rows"));
        columns = Integer.parseInt(properties.getProperty("columns"));
        alivePercentage = Double.parseDouble(properties.getProperty("alive"));
        tick = Long.parseLong(properties.getProperty("tick"));
        logDensityPeriod = Integer.parseInt(properties.getProperty("logDensityPeriod"));
        mode = SimulationMode.valueOf(properties.getProperty("mode"));
        birthNeighboursCount = Integer.parseInt(properties.getProperty("birthNeighboursCount"));
        overcrowdingNeighboursCount = Integer.parseInt(properties.getProperty("overcrowdingNeighboursCount"));
        lonelinessNeighboursCount = Integer.parseInt(properties.getProperty("lonelinessNeighboursCount"));
    }

}
