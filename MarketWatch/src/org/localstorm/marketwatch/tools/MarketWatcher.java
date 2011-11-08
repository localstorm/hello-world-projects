package org.localstorm.marketwatch.tools;

import org.localstorm.marketwatch.*;
import org.localstorm.marketwatch.alert.Alerting;
import org.localstorm.marketwatch.alert.SmsAlerting;
import org.localstorm.marketwatch.pricing.*;
import org.localstorm.marketwatch.strategies.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MarketWatcher {


    private static final long HOUR = 60 * 60 * 1000;

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: assetName strategy");
            return;
        }

        String assetName = args[0];
        String strategyName = args[1];

        Alerting alerting = new SmsAlerting();

        StrategyParams bsp = StrategyParams.load(new File("player_conf/" + assetName + "/" + strategyName + "_buy.properties"));
        StrategyParams ssp = StrategyParams.load(new File("player_conf/" + assetName + "/" + strategyName + "_sell.properties"));

        LiquidityPool pool = new LiquidityPool(0, 0);

        pool.init(new File("player_conf/" + assetName + "/liquidity.properties"));

        TxLog txLog = new TxLog();
        Possessions possessions = new Possessions();

        PriceBoard board = new PriceBoard();
        Environment env = Environment.getEnv(board, pool, possessions, txLog);
        PurchaseStrategy buyStrategy = new BuyTheDeepChunked(env, bsp);
        SellStrategy sellStrategy = new SellTheTopChunked(env, ssp);

        MarketSignalSource msr = new MarketSignalReader(new File("data/" + assetName + ".mkt.csv"));
        MarketSignal signal;

        boolean tradingStarted = false;
        boolean historyReplaying = true;
        long lastPnLSent = 0;

        do {
            while ((signal = safeReadSignal(msr)) != null) {
                EventType event = signal.getEventType();

                if (event.equals(EventType.StartTrading)) {
                    tradingStarted = true;
                    System.out.println("--->>>>> Trading started --->>>>>");
                    continue;
                }

                //PriceChange
                Asset a = new Asset();
                a.setName(signal.getAsset());
                a.setPrice(signal.getPrice());
                a.setQuantity(0.0);

                if (event.equals(EventType.Transaction)) {
                    executeTx(a, signal, possessions, pool, board, txLog);
                    continue;
                }

                if (txLog.getLastPrice(a) == null) {
                    Tx tx = new Tx(a.getName(), BuySell.SELL, 0, a.getPrice());
                    txLog.logOperation(tx);
                }

                board.updatePrice(a);
                System.out.println("Current price: " + a.getPrice()+" at "+signal.getDateTime());

                if (!tradingStarted) {
                    continue;
                }

                double amountToBuy = buyStrategy.onPriceChange(a);
                double amountToSell = sellStrategy.onPriceChange(a);
                double totalBuy = amountToBuy - amountToSell;

                if (!historyReplaying && tradingStarted && (lastPnLSent < (System.currentTimeMillis() - HOUR)) && businessHours()) {
                    alerting.sendMessage("Current profit: " + getProfit(possessions, board, txLog, a));
                    lastPnLSent = System.currentTimeMillis();
                }

                if (totalBuy > 0) {
                    System.out.println("SIGNAL: Buying: " + totalBuy + " of " + a.getName() + " at " + a.getPrice());

                    double withdraw = a.getPrice().getBuy() * totalBuy;


                    if (pool.getCash(withdraw)) {
                        System.out.println("EXECUTE: Buy: " + totalBuy + " of " + a.getName() + " at " + a.getPrice());
                        if (!historyReplaying) {
                            alerting.sendMessage("Buy: " + totalBuy + " of " + a.getName() + " at " + a.getPrice());
                        }
                        possessions.add(a.getName(), totalBuy);
                        txLog.logOperation(new Tx(a.getName(), BuySell.BUY, totalBuy, a.getPrice()));
                        dumpAccounting(txLog, pool, board, possessions);
                        board.resetPriceMinMax(a);
                    } else {
                        System.out.println("NOT EXECUTED: Can't withdraw " + withdraw + " as no more cash");
                    }
                }

                if (totalBuy < 0) {
                    double sell = -totalBuy;
                    System.out.println("SIGNAL: Selling: " + sell + " of " + a.getName());

                    if (possessions.reduce(a.getName(), sell)) {
                        System.out.println("EXECUTED: Sold: " + sell + " of " + a.getName() + " at " + a.getPrice());
                        if (!historyReplaying) {
                            alerting.sendMessage("Sell: " + sell + " of " + a.getName() + " at " + a.getPrice());
                        }

                        pool.putCash(a.getPrice().getSell() * sell);
                        txLog.logOperation(new Tx(a.getName(), BuySell.SELL, sell, a.getPrice()));
                        holdIfNeeded(pool, board, possessions);
                        dumpAccounting(txLog, pool, board, possessions);
                        board.resetPriceMinMax(a);
                    } else {
                        System.out.println("NO EXECUTION: Can't sell " + a.getName() + ". Nothing left to sell");
                    }
                }
                if (!historyReplaying) {
                    dumpAccounting(txLog, pool, board, possessions);
                    Thread.sleep(60000);
                }
            }
            dumpAccounting(txLog, pool, board, possessions);
            msr.close();

            //TODO
            if (assetName.equals("Silver")) {
                msr = new NomosSource(assetName, null);
            } else {
                msr = new NomosSource(null, assetName);
            }
            historyReplaying = false;
        } while (true);

    }

    private static boolean businessHours() {
        Calendar c = Calendar.getInstance();

        int dow = c.get(Calendar.DAY_OF_WEEK);
        if (Calendar.SATURDAY == dow ||
                Calendar.SUNDAY == dow) {
            return false;
        }

        int hour = c.get(Calendar.HOUR_OF_DAY);
        return (hour >= 9 && hour <= 20);
    }

    private static double getProfit(Possessions poss, PriceBoard board, TxLog txLog, Asset a) {
        double profit = txLog.getProfitChangeSinceLastSell(a, a.getPrice());
        double curValue = poss.getValue(a.getName(), board);

        if (curValue == 0) {
            System.out.println("SellStrategy >>> Nothing to sell");
            return 0;
        }

        return curValue / (curValue - profit) - 1;
    }

    private static MarketSignal safeReadSignal(MarketSignalSource msr) {
        try {
            return msr.readSignal();
        } catch (Exception e) {
            return null;
        }
    }


    private static void executeTx(Asset a, MarketSignal signal, Possessions possessions, LiquidityPool pool, PriceBoard board, TxLog txLog) throws IOException {
        double buyAmt = signal.getQuantity();
        if (buyAmt >= 0) {
            double withdraw = a.getPrice().getBuy() * buyAmt;
            pool.getCash(withdraw);
            possessions.add(signal.getAsset(), buyAmt);
            txLog.logOperation(new Tx(a.getName(), BuySell.BUY, buyAmt, a.getPrice()));
            dumpAccounting(txLog, pool, board, possessions);
            board.resetPriceMinMax(a);
        } else {
            double sell = -buyAmt;
            possessions.reduce(a.getName(), sell);
            pool.putCash(a.getPrice().getSell() * sell);
            txLog.logOperation(new Tx(a.getName(), BuySell.SELL, sell, a.getPrice()));
            holdIfNeeded(pool, board, possessions);
            dumpAccounting(txLog, pool, board, possessions);
            board.resetPriceMinMax(a);
        }
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
