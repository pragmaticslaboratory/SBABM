package simulation;

import agent.Buyer;
import agent.Market;
import gui.Chart;
import inputManager.Configuration;
import utils.Console;
import reporter.ReportRegister;
import reporter.Reporter;
import scenarios.ScenarioManager;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements FlyWeight, Step, ReportRegister {
    public static int ID = 0;

    private final int periods;
    private final List<Buyer> buyers;
    private final List<Market> markets;

    public Simulation(List<Buyer> buyers, List<Market> markets, int periods) {
        this.periods = periods;
        this.buyers = buyers;
        this.markets = markets;

        reinit();
        Console.info("Simulation: created with " + buyers.size() + " buyers and " + markets.size() + " markets");
    }

    @Override
    public void reinit() {
        ++Simulation.ID;
        buyers.iterator().forEachRemaining(Buyer::reinit);
        buyers.iterator().forEachRemaining(buyer -> buyer.setFriends(buyers));
        buyers.iterator().forEachRemaining(buyer -> buyer.setKnowMarkets(filterQuota(markets)));
        buyers.iterator().forEachRemaining(Buyer::setInitialEndorsements);

        markets.iterator().forEachRemaining(Market::reinit);
        System.gc(); //clean memory
    }

    private List<Market> filterQuota(List<Market> markets) {
        if (!Configuration.MARKET_QUOTA) {
            return markets; //all markets
        }

        List<Market> filteredMarket = new ArrayList<>();

        double random;
        for (Market mk : markets) {
            random = Math.random();
            if (random < mk.getQuota()) {
                filteredMarket.add(mk);
            }
        }

        return filteredMarket;
    }

    private void generateSalesPerData(int period) {
        int[] sales = new int[markets.size()];
        int[] uniqueSales = new int[markets.size()];

        buyers.iterator().forEachRemaining(buyer -> {
            Market selectedMarket = buyer.getLastSelectMarked(period);

            if (selectedMarket != null) {
                selectedMarket.addBuyers(buyer.getID());
                sales[selectedMarket.getID()]++;
            }
        });

        markets.iterator().forEachRemaining(market -> uniqueSales[market.getID()] = market.getUniqueSales());

        Reporter.addSalesByMarketData(ID, period, sales);
        Reporter.addSalesUniqueByMarketData(ID, period, uniqueSales);
    }

    public void run() {
        Console.info("Simulation: Starting " + Simulation.ID);

        for (int period = 1; period <= periods; ++period) {
            doStep(period);
            Console.debug("Simulation: Period " + period);

            ScenarioManager.apply(period);
            report(period);

            if (Configuration.WOM) {
                for (Buyer buyer : buyers) {
                    buyer.receiveRecommendation(period);
                }
            }
        }

        if (Configuration.GUI) {
            //Chart.displaySelection(buyers, markets);
            Chart.displaySales(markets);
        }

        reinit();
    }


    @Override
    public void doStep(int period) {
        buyers.iterator().forEachRemaining(buyer -> buyer.doStep(period));
    }

    @Override
    public void report(int period) {
       if (period > Configuration.LEARNING_PERIODS) generateSalesPerData(period);
        buyers.iterator().forEachRemaining(buyer -> Reporter.addEndorsementData(buyer.getEndorsementData(period)));
    }

    @Override
    public String toString() {
        return "Simulation{" +
                "ID=" + Simulation.ID +
                ", periods=" + periods +
                ", buyers=" + buyers.size() +
                ", markets=" + markets.size() +
                '}';
    }
}
