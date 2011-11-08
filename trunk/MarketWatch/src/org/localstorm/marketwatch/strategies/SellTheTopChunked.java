package org.localstorm.marketwatch.strategies;

import org.localstorm.marketwatch.*;
import org.localstorm.marketwatch.pricing.PriceBoard;
import org.localstorm.marketwatch.strategies.signals.SignalFactory;

public class SellTheTopChunked implements SellStrategy {

    private LiquidityPool pool;
    private Possessions poss;
    private TxLog txLog;
    private double profitChangeFactor;
    private double maxSpread;
    private PriceBoard board;
    private boolean dynamicChunks;
    private boolean dynamicSpreads;
    private boolean maximizeOperationVolume;

    public SellTheTopChunked(Environment env, StrategyParams sp) {
        this.board = env.getBoard();
        this.pool = env.getPool();
        this.poss = env.getPossessions();
        this.txLog = env.getTxLog();
        this.profitChangeFactor = sp.getDeviationFactor();
        this.maxSpread = sp.getSpreadCap();
        this.dynamicChunks = sp.isDynamicChunks();
        this.dynamicSpreads = sp.isDynamicSpread();
        this.maximizeOperationVolume = sp.isMaximizeOperationVolume();
    }

    public double onPriceChange(Asset changed) {

        double profit = txLog.getProfitChangeSinceLastSell(changed, changed.getPrice());
        double curValue = poss.getValue(changed.getName(), board);

        if (curValue == 0) {
            System.out.println("SellStrategy >>> Nothing to sell");
            return 0;
        }

        double profitFactor = curValue / (curValue - profit) - 1;

        if (profit < 0) {
            System.out.println("SellStrategy >>> Still no profit (" + profitFactor + "), holding");
            return 0;
        }

        double spread = changed.getPrice().getSpread();
        System.out.println("SellStrategy >>> Current profit factor: " + profitFactor);
        System.out.println("SellStrategy >>> Current spread: " + spread);

        if (!isSpreadAcceptable(profitFactor, spread)) {
            System.out.println("SellStrategy >>> Too big spread.");
            return 0;
        }

        if (profitFactor >= profitChangeFactor) {
            System.out.println("SellStrategy >>> Profit factor: " + profitFactor + ". Selling");
            return SignalFactory.roundedFixedCash(changed.getPrice().getSell(), getSellVolume(changed, profitFactor));
        } else {
            System.out.println("SellStrategy >>> Small profit, holding");
        }

        return 0;
    }

    private double getSellVolume(Asset changed, double profitFactor) {
        double toSell;
        if (!maximizeOperationVolume) {
            double chunkFactor = (dynamicChunks) ? profitFactor / profitChangeFactor : 1;
            double chunk = pool.getChunkSize() * chunkFactor;

            double total = poss.getValue(changed.getName(), board);
            toSell = (total >= chunk) ? chunk : total;

            System.out.println("SellStrategy >>> ChunkFactor: " + chunkFactor + ", Chunk: " + chunk);
        } else {
            toSell = poss.getValue(changed.getName(), board);
        }
        return toSell;
    }

    private boolean isSpreadAcceptable(double profitFactor, double spread) {
        if (dynamicSpreads) {
            double spreadThreshold = maxSpread * (profitFactor / profitChangeFactor);
            System.out.println("SellStrategy >>> SpreadFactor: " + spreadThreshold + ", Spread: " + spread);
            return spread <= spreadThreshold;
        } else {
            System.out.println("SellStrategy >>> SpreadFactor: " + 1.0 + ", Spread: " + spread);
            return spread <= maxSpread;
        }
    }


}
