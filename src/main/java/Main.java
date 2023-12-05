import enums.SimulationMode;
import world.World;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {

        Config.load();

        Frame frame = new Frame("Game of Life");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - Config.width) / 2;
        int y = (screenSize.height - Config.height) / 2;
        frame.setSize(Config.width, Config.height);
        frame.setLocation(x, y);


        World world = new World(Config.rows, Config.columns, Config.alivePercentage,
                Config.birthNeighboursCount, Config.overcrowdingNeighboursCount, Config.lonelinessNeighboursCount);
        frame.add(world.getWorld());

        // Handle window close event
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        // Make the frame visible
        frame.setVisible(true);

        System.out.println("Using " + Config.mode + " mode");

        int generation = 0;
        boolean running = true;
        while (running) {
            generation++;
            try {
                Thread.sleep(Config.tick);
            } catch (InterruptedException e) {
                running = false;
            }
            if (generation % Config.logDensityPeriod == 0) {
                System.out.println("[Generation #" + generation + "]: density = " + String.format("%.4f", world.getDensity()));
            }
            if (Config.mode == SimulationMode.ASYNCH) {
                world.updateAsynch();
            } else {
                world.updateSynch();
            }
        }
    }

}
