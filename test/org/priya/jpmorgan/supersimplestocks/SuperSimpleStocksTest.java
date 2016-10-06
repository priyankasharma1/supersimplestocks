package org.priya.jpmorgan.supersimplestocks;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.priya.jpmorgan.supersimplestock.services.IStockFormulas;
import org.priya.jpmorgan.supersimplestocks.StockFormulas;
import org.priya.jpmorgan.supersimplestocks.TradingService;
import org.priya.jpmorgan.supersimplestocks.model.Stock;

/**
 * GBCE Trading Place Test.
 *
 * @author priya
 */
public class SuperSimpleStocksTest
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


  /**
   * Would need better test data !!!
   */
  @Test
  public void testRequirements()
  {
    GBCETradingPlace gbceTradingPlace = new GBCETradingPlace(0l);

    double[] marketPrice = { 0.1, 0.1, 0.1, 0.1, 0.1 };
    double[] dividendYield = { 0.0, 0.8, 2.3, 0.02, 1.3 };
    double[] peRatio = { Double.POSITIVE_INFINITY, 1.25, 0.434, 50.0, 0.769 };
    double[] vwsp1 = { 0.55, 0.55, 0.55, 0.55, 0.55 };
    double[] vwsp2 = { 1.445, 1.445, 1.445, 1.445, 1.445 };

    int iStock = 0;
    for (Stock stock : gbceTradingPlace.stocks)
    {
      assertEquals(dividendYield[iStock], stockFormulas.ComputeDividendYield(stock.getSymbol(), marketPrice[iStock]), DELTA);
      assertEquals(peRatio[iStock], stockFormulas.ComputePERatio(stock.getSymbol(), marketPrice[iStock]), DELTA);
      assertEquals(vwsp1[iStock],
    		  stockFormulas.ComputeVolumeWeightedStockPrice(TradingService
                       .filterTradesByTimestamp(TradingService.filterTradesByStock(gbceTradingPlace.trades, stock),
                                                15 * 60 * 1000, 15 * 60 * 1000)),
                   DELTA);
      assertEquals(vwsp2[iStock],
    		  stockFormulas.ComputeVolumeWeightedStockPrice(TradingService
                       .filterTradesByTimestamp(TradingService.filterTradesByStock(gbceTradingPlace.trades, stock),
                                                30 * 60 * 1000, 15 * 60 * 1000)),
                   DELTA);
      iStock++;
    }

    assertEquals(0.816, StockFormulas.ComputeGeometricMean(gbceTradingPlace.trades), DELTA);
  }
}
