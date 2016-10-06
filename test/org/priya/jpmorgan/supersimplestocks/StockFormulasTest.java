package org.priya.jpmorgan.supersimplestocks;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.priya.jpmorgan.supersimplestock.services.IStockFormulas;
import org.priya.jpmorgan.supersimplestocks.StockFormulas;
import org.priya.jpmorgan.supersimplestocks.model.Stock;
import org.priya.jpmorgan.supersimplestocks.model.Trade;
import org.priya.jpmorgan.supersimplestocks.model.StockType;
import org.priya.jpmorgan.supersimplestocks.model.TradeIndicator;

/**
 * Test for Stocks formulas.
 *
 * @author priya
 */
public class StockFormulasTest
{
  private static final double DELTA = 0.001;
  
  private IStockFormulas stockFormulas;

  /**
   * Would need better test data !!!
   */
  
  @Before
  public void setupData()
  {
	  stockFormulas = new StockFormulas();
  }

  @Test
  public void testComputeDividendYieldFromLastDividend()
  {
    assertEquals(0.02, stockFormulas.ComputeDividendYield("GIN", 0.1), DELTA);
  }

  @Test
  public void testComputePERatio()
  {
   // assertEquals(1.25, stockFormulas.ComputePERatio("GIN", 0.1), DELTA);
    assertEquals(50.0, stockFormulas.ComputePERatio("GIN", 0.1), DELTA);
  }

  @Test
  public void testComputeGeometricMean()
  {
    Stock stock = new Stock("TEA", StockType.COMMON, 0.0, 0.0, 1.0);
    List<Trade> trades = new ArrayList<>();
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.10, 10, System.currentTimeMillis()));
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.09, 10, System.currentTimeMillis()));
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.08, 10, System.currentTimeMillis()));
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.09, 10, System.currentTimeMillis()));
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.1, 10, System.currentTimeMillis()));
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.11, 10, System.currentTimeMillis()));
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.12, 10, System.currentTimeMillis()));
    assertEquals(0.097, StockFormulas.ComputeGeometricMean(trades), DELTA);
  }

  @Test
  public void testComputeVolumeWeightedStockPrice()
  {
    Stock stock = new Stock("TEA", StockType.COMMON, 0.0, 0.0, 1.0);
    List<Trade> trades = new ArrayList<>();
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.10, 10, System.currentTimeMillis()));
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.09, 10, System.currentTimeMillis()));
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.08, 10, System.currentTimeMillis()));
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.09, 10, System.currentTimeMillis()));
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.1, 10, System.currentTimeMillis()));
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.11, 10, System.currentTimeMillis()));
    trades.add(new Trade(stock, TradeIndicator.BUY, 0.12, 10, System.currentTimeMillis()));

    assertEquals(0.0985, stockFormulas.ComputeVolumeWeightedStockPrice(trades), DELTA);
  }
}
