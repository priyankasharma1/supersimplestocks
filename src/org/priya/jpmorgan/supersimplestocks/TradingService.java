package org.priya.jpmorgan.supersimplestocks;

import java.util.List;
import java.util.stream.Collectors;

import org.priya.jpmorgan.supersimplestock.services.ITradingService;
import org.priya.jpmorgan.supersimplestocks.dao.StocksEntityManager;
import org.priya.jpmorgan.supersimplestocks.model.Stock;
import org.priya.jpmorgan.supersimplestocks.model.Trade;
import org.priya.jpmorgan.supersimplestocks.model.TradeIndicator;

/**
 * Trading service.
 *
 * @author priya
 */
public class TradingService implements ITradingService
{
  /**
   * Buy stock.
   *
   * @param stock
   * @param price
   * @param quantity
   * @return
   */
	
	private static StocksEntityManager stocksEntityManager = null;


	/**
	 * 
	 * @param stocksEntityManager
	 */
	public void setStocksEntityManager(StocksEntityManager stocksEntityManager) {
		this.stocksEntityManager = stocksEntityManager;
	}
	
  public static Trade buyStock(Stock stock, double price, int quantity, long timestamp)
  {
    return new Trade(stock, TradeIndicator.BUY, price, quantity, timestamp);
  }

  /**
   * Sell stock
   * 
   * @param stock
   * @param price
   * @param quantity
   * @param timestamp
   * @return
   */
  public static Trade sellStock(Stock stock, double price, int quantity, long timestamp)
  {
    return new Trade(stock, TradeIndicator.SELL, price, quantity,
        timestamp);
  }

  /**
   * Filter trades by stock.
   *
   * @param trades
   * @param stock
   * @return
   */
  public static List<Trade> filterTradesByStock(List<Trade> trades, Stock stock)
  {
    return trades.stream().filter(trade -> trade.stock == stock).collect(Collectors.toList());
  }

  /**
   * Filter trades by timestamp.
   *
   * @param trades
   * @param time
   * @param deltaTimeInThePast
   * @return
   */
  public static List<Trade> filterTradesByTimestamp(List<Trade> trades, long time, long deltaTimeInThePast)
  {
    return trades.stream().filter(trade -> (time - deltaTimeInThePast <= trade.timestamp && trade.timestamp <= time))
        .collect(Collectors.toList());
  }
  
	/*
	 * (non-Javadoc)
	 * @see com.jpmorgan.stocks.simple.services.SimpleStockService#recordTrade(com.jpmorgan.stocks.simple.model.Trade)
	 */
	public boolean recordTrade(Trade trade) throws Exception{
		boolean recordResult = false;
		try{
			System.out.println("Begin recordTrade with trade object: ");
			System.out.println(trade);

			// trade should be an object
			if(trade==null){
				throw new Exception("Trade object to record should be a valid object and it's null.");
			}

			// stock should be an object
			if(trade.getStock()==null){
				throw new Exception("A trade should be associated with a stock and the stock for the trade is null.");
			}

			// shares quantity should be greater than zero
			if(trade.getQuantity()<=0){
				throw new Exception("Shares quantity in the trade to record should be greater than cero.");
			}

			// shares price should be greater than zero
			if(trade.getPrice()<=0.0){
				throw new Exception("Shares price in the trade to record should be greater than cero.");
			}

			recordResult = stocksEntityManager.recordTrade(trade);

		}catch(Exception exception){
			System.err.println("Error when trying to record a trade." + exception);
			throw new Exception("Error when trying to record a trade.", exception);
		}
		return recordResult;
	}

}
