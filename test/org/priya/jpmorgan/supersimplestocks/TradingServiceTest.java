package org.priya.jpmorgan.supersimplestocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.priya.jpmorgan.supersimplestocks.TradingService;
import org.priya.jpmorgan.supersimplestocks.model.Stock;
import org.priya.jpmorgan.supersimplestocks.model.Trade;
import org.priya.jpmorgan.supersimplestocks.model.StockType;
import org.priya.jpmorgan.supersimplestocks.model.TradeIndicator;

/**
 * Test for Trading service.
 *
 * @author priya
 */
public class TradingServiceTest
{
  private static final double DELTA = 0.001;

  /**
   * Would need better test data !!!
   */

  @Test
  public void testBuyStock()
  {
    Stock stock = new Stock("GIN", StockType.COMMON, 0.08, 0.02, 1.0);
    long timestamp = System.currentTimeMillis();
    Trade trade = TradingService.buyStock(stock, 0.1, 10, timestamp);
    assertEquals(stock, trade.stock);
    assertEquals(TradeIndicator.BUY, trade.indicator);
    assertEquals(0.1, trade.price, DELTA);
    assertEquals(10, trade.quantity);
    assertEquals(timestamp, trade.timestamp);
  }

  @Test
  public void testSellStock()
  {
    Stock stock = new Stock("GIN", StockType.COMMON, 0.08, 0.02, 1.0);
    long timestamp = System.currentTimeMillis();
    Trade trade = TradingService.sellStock(stock, 0.1, 10, timestamp);
    assertEquals(stock, trade.stock);
    assertEquals(TradeIndicator.SELL, trade.indicator);
    assertEquals(0.1, trade.price, DELTA);
    assertEquals(10, trade.quantity);
    assertEquals(timestamp, trade.timestamp);
  }

  @Test
  public void testFilterTradeByStock()
  {
    GBCETradingPlace gbceTradingPlace = new GBCETradingPlace(0);
    Stock stock = gbceTradingPlace.stocks.get(0);
    List<Trade> stockTrades = TradingService.filterTradesByStock(gbceTradingPlace.trades, stock);
    assertEquals(180, stockTrades.size());
    for (Trade stockTrade : stockTrades)
    {
      assertEquals(stock, stockTrade.stock);
    }
  }

  @Test
  public void testFilterTradeByTimestamp()
  {
    GBCETradingPlace gbceTradingPlace = new GBCETradingPlace(0);

    testFilterTradeByTimestamp(gbceTradingPlace.trades, 15 * 60 * 1000, 15 * 60 * 1000, 455);
    testFilterTradeByTimestamp(gbceTradingPlace.trades, 30 * 60 * 1000, 15 * 60 * 1000, 450);
  }

  protected void testFilterTradeByTimestamp(List<Trade> trades, long time, long deltaTimeInThePast, int expectedSize)
  {
    List<Trade> stockTrades = TradingService.filterTradesByTimestamp(trades, time, deltaTimeInThePast);
    assertEquals(expectedSize, stockTrades.size());
    for (Trade stockTrade : stockTrades)
    {
      assertTrue(time - deltaTimeInThePast <= stockTrade.timestamp && stockTrade.timestamp <= time);
    }
  }
}
