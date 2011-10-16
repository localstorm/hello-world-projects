package org.localstorm.marketwatch.tools;

import org.localstorm.marketwatch.*;
import org.localstorm.marketwatch.pricing.*;
import org.localstorm.marketwatch.strategies.*;

import java.io.File;
import java.util.List;

public class VirtualPlayer {


    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: assetName strategy");
            return;
        }

        String assetName = args[0];
        String strategyName = args[1];

        StrategyParams bsp = StrategyParams.load(new File("player_conf/" + assetName + "/" + strategyName + "_buy.properties"));
        StrategyParams ssp = StrategyParams.load(new File("player_conf/" + assetName + "/" + strategyName + "_sell.properties"));

        LiquidityPool pool = new LiquidityPool(0, 0);

        pool.init(new File("player_conf/" + assetName + "/liquidity.properties"));

        TxLog txLog = new TxLog(new File("player_conf/" + assetName + "/tx.csv"), false);
        Possessions possessions = txLog.load(pool);

        PriceBoard board = new PriceBoard();
        Environment env = Environment.getEnv(board, pool, possessions, txLog);
        PurchaseStrategy buyStrategy = new BuyTheDeepChunked(env, bsp);
        SellStrategy sellStrategy = new SellTheTopChunked(env, ssp);

        PricingSource pricer = new CsvSource(assetName, new File("data/" + assetName + ".csv"));

        while (pricer.updatePrices()) {
            List<Asset> prices = pricer.getPrices();
            if (prices.isEmpty()) {
                return;
            }

            for (Asset a : prices) {
                board.updatePrice(a);

                System.out.println("MARKET: Current price: " + a.getPrice());

                double amountToBuy = buyStrategy.onPriceChange(a);
                double amountToSell = sellStrategy.onPriceChange(a);

                double totalBuy = amountToBuy - amountToSell;

                if (totalBuy > 0) {
                    System.out.println("SIGNAL: Buying: " + totalBuy + " of " + a.getName() + " at " + a.getPrice());

                    double withdraw = a.getPrice().getBuy() * totalBuy;
                    if (pool.getCash(withdraw)) {
                        System.out.println("EXECUTED: Buy: " + totalBuy + " of " + a.getName() + " at " + a.getPrice());
                    } else {
                        System.out.println("NOT EXECUTED: Can't withdraw " + withdraw + " as no more cash");
                        continue;
                    }

                    possessions.add(a.getName(), totalBuy);
                    txLog.logOperation(new Tx(a.getName(), BuySell.BUY, totalBuy, a.getPrice()));
                    dumpAccounting(txLog, pool, board, possessions);
                    board.resetPriceMinMax(a);
                }

                if (totalBuy < 0) {
                    double sell = -totalBuy;
                    System.out.println("SIGNAL: Selling: " + sell + " of " + a.getName());

                    if (possessions.reduce(a.getName(), sell)) {
                        System.out.println("EXECUTED: Sold: " + sell + " of " + a.getName() + " at " + a.getPrice());
                    } else {
                        System.out.println("NOT EXECUTION: Can't sell " + a.getName() + ". Nothing left to sell");
                        continue;
                    }

                    pool.putCash(a.getPrice().getSell() * sell);
                    txLog.logOperation(new Tx(a.getName(), BuySell.SELL, sell, a.getPrice()));
                    holdIfNeeded(pool, board, possessions);
                    dumpAccounting(txLog, pool, board, possessions);
                    board.resetPriceMinMax(a);
                }
            }
        }
        dumpAccounting(txLog, pool, board, possessions);
    }

    private static void holdIfNeeded(LiquidityPool pool, PriceBoard board, Possessions possessions) {
        if (pool.getCashLeft() + possessions.getValue(board) > pool.getHoldCap()) {
            pool.hold(pool.getCashLeft() + possessions.getValue(board) - pool.getHoldCap());
        }
    }

    private static void dumpAccounting(TxLog txLog, LiquidityPool pool, PriceBoard prices, Possessions possessions) {

        double total = pool.getOnHold() + pool.getCashLeft() + possessions.getValue(prices);

        System.out.println("------ Current balance ------");
        System.out.println("Liquidity left:     " + pool.getCashLeft());
        System.out.println("Liquidity on hold:  " + pool.getOnHold());
        System.out.println("Possessions value:  " + possessions.getValue(prices));
        System.out.println("Total:              " + total);
        System.out.println("Tx Count:           " + (txLog.size()));
        System.out.println("-----------------------------");
    }

}
