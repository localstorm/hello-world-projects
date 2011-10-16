package org.localstorm.marketwatch;

import org.localstorm.marketwatch.pricing.PriceBoard;

public class Environment {

    private PriceBoard board;
    private LiquidityPool pool;
    private Possessions possessions;
    private TxLog txLog;

    public PriceBoard getBoard() {
        return board;
    }

    public void setBoard(PriceBoard board) {
        this.board = board;
    }

    public LiquidityPool getPool() {
        return pool;
    }

    public void setPool(LiquidityPool pool) {
        this.pool = pool;
    }

    public Possessions getPossessions() {
        return possessions;
    }

    public void setPossessions(Possessions possessions) {
        this.possessions = possessions;
    }

    public TxLog getTxLog() {
        return txLog;
    }

    public void setTxLog(TxLog txLog) {
        this.txLog = txLog;
    }

    public static Environment getEnv(PriceBoard board, LiquidityPool pool, Possessions pos, TxLog txLog) {
        Environment e = new Environment();
        e.setBoard(board);
        e.setPool(pool);
        e.setPossessions(pos);
        e.setTxLog(txLog);
        return e;
    }
}
