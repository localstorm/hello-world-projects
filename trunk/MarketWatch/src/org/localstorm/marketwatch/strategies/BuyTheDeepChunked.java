package org.localstorm.marketwatch.strategies;

import org.localstorm.marketwatch.*;
import org.localstorm.marketwatch.pricing.PriceBoard;
import org.localstorm.marketwatch.strategies.signals.SignalFactory;


/**
 * User: LocalStorm
 * Date: 10/8/11
 * Time: 9:23 AM
 */
public class BuyTheDeepChunked implements PurchaseStrategy {
    private LiquidityPool pool;
    private double priceChangeFactor;
    private TxLog txLog;
    private PriceBoard board;
    private double maxSpread;
    private boolean dynamicChunks;
    private boolean dynamicSpread;

    public BuyTheDeepChunked(Environment env, StrategyParams sp) {
        this.pool = env.getPool();
        this.txLog = env.getTxLog();
        this.board = env.getBoard();

        this.maxSpread = sp.getSpreadCap();
        this.priceChangeFactor = sp.getDeviationFactor();
        this.dynamicChunks = sp.isDynamicChunks();
        this.dynamicSpread = sp.isDynamicSpread();
    }

    public double onPriceChange(Asset changed) {
        double sellPriceMax = board.getSellMinMax(changed).getMax();
        double lastTxSellPrice = getLastPrice(changed).getSell();
        double buyPriceMax = board.getBuyMinMax(changed).getMax();
        double lastTxBuyPrice = getLastPrice(changed).getBuy();

        Price newPrice = changed.getPrice();

        double sellPriceChangeFromPeakFactor = newPrice.getSell() / sellPriceMax - 1;
        double sellPriceChangeFromLastTxFactor = newPrice.getSell() / lastTxSellPrice - 1;
        double buyPriceChangeFromPeakFactor = newPrice.getBuy() / buyPriceMax - 1;
        double buyPriceChangeFromLastTxFactor = newPrice.getBuy() / lastTxBuyPrice - 1;
        double spread = changed.getPrice().getSpread();

        System.out.println("BuyStrategy >>> Sell change from last peak ("+sellPriceMax+"): " + sellPriceChangeFromPeakFactor);
        System.out.println("BuyStrategy >>> Buy change from last peak ("+buyPriceMax+"): " + buyPriceChangeFromPeakFactor);
        System.out.println("BuyStrategy >>> Sell change from last tx ("+lastTxSellPrice+"): " + sellPriceChangeFromLastTxFactor);
        System.out.println("BuyStrategy >>> Buy change from last tx ("+lastTxBuyPrice+"): " + buyPriceChangeFromLastTxFactor);
        System.out.println("BuyStrategy >>> Current spread: " + spread);


        if (sellPriceChangeFromLastTxFactor <= -priceChangeFactor &&
                buyPriceChangeFromLastTxFactor <= -priceChangeFactor) {

            double avgPCF = -Util.avg(sellPriceChangeFromLastTxFactor, buyPriceChangeFromLastTxFactor);

            if (!isSpreadAcceptable(avgPCF, spread)) {
                System.out.println("BuyStrategy >>> Too big spread.");
                return 0;
            }

            double chunkFactor = dynamicChunks ? avgPCF / priceChangeFactor : 1.0;
            double chunk = getChunkSize(chunkFactor, pool.getChunkSize());
            System.out.println("BuyStrategy >>> ChunkFactor: " + chunkFactor + ", Chunk: " + chunk);
            return SignalFactory.roundedFixedCash(changed.getPrice().getBuy(), chunk);
        }


        if ((sellPriceMax > lastTxSellPrice && sellPriceChangeFromPeakFactor <= -priceChangeFactor) &&
                (buyPriceMax > lastTxBuyPrice && buyPriceChangeFromPeakFactor <= -priceChangeFactor)) {

            if (!isSpreadAcceptable(priceChangeFactor, spread)) {
                System.out.println("BuyStrategy >>> Too big spread.");
                return 0;
            }

            double chunk = getChunkSize(1, pool.getChunkSize());
            System.out.println("BuyStrategy >>> ChunkFactor: " + 1.0 + ", Chunk: " + chunk);
            return SignalFactory.roundedFixedCash(changed.getPrice().getBuy(), chunk);

        }

        return 0;
    }

    private boolean isSpreadAcceptable(double avgPCF, double spread) {
        if (dynamicSpread) {
            double spreadThreshold = maxSpread * (avgPCF / priceChangeFactor);
            System.out.println("BuyStrategy >>> SpreadFactor: "+spreadThreshold+", Spread: "+spread);
            return spread <= spreadThreshold;
        } else {
            System.out.println("BuyStrategy >>> SpreadFactor: "+1.0+", Spread: "+spread);
            return spread <= maxSpread;
        }
    }

    private double getChunkSize(double factor, double chunkSize) {
        double left = pool.getCashLeft();
        return Math.min(left, chunkSize * factor);
    }

    private Price getLastPrice(Asset changed) {
        return txLog.getLastPrice(changed);
    }

}
