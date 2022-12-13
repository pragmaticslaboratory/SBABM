import agent.BuyerFactory;
import agent.MarketFactory;
import inputManager.Configuration;
import inputManager.Loader;
import utils.Console;
import reporter.Reporter;
import simulation.Simulation;

import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        Loader.load(args.length > 0 ? args[0] : "");

        Console.info("MAIN: Configuration loaded -> {" + Configuration.toStringConfiguration() + " }");
        Simulation s = new Simulation(BuyerFactory.createFromInput(), MarketFactory.createFromInput(),
                Configuration.PERIODS);

        Instant start = Instant.now();
        for (int i = 1; i <= Configuration.REPETITIONS + 1; ++i) {
            Console.info(s);
            s.run();
        }
        Instant end = Instant.now();

        Reporter.write();
        Duration timeElapsed = Duration.between(start, end);
        Console.info("Main: Simulation executions took " + timeElapsed.toMinutes() + " mins");
        Console.end("Main: End.");
    }
}
