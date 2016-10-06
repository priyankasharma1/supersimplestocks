package org.priya.jpmorgan.supersimplestocks.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.priya.jpmorgan.supersimplestocks.model.Stock;
import org.priya.jpmorgan.supersimplestocks.model.StockType;
import org.priya.jpmorgan.supersimplestocks.model.Trade;



/**
 * Implementation of the Stock Entity Manager.
 * 
 * @author priya
 *
 */
public class StocksEntityManagerImpl implements StocksEntityManager {



	/**
	 * 
	 */
	private Map<String, Stock> stocks = null;

	/**
	 * 
	 */
	private ArrayList<Trade> trades = null;

	private ArrayList<Stock> stocksList = new ArrayList<Stock>();;

	/**
	 * 
	 */
	public StocksEntityManagerImpl(){
		trades = new ArrayList<Trade>();
		stocks = initializeStocksMap();
	}
	
	  protected Map<String,Stock> initializeStocksMap()
	  {
	    // this is the stocks data.
		// ToDo:- Need to make it springs bean and then inject this data from springs config file.
	    stocksList.add(new Stock("TEA", StockType.COMMON, 0.0, 0.0, 0.1));
	    stocksList.add(new Stock("POP", StockType.COMMON, 0.08, 0.0, 0.1));
	    stocksList.add(new Stock("ALE", StockType.COMMON, 0.23, 0.0, 0.06));
	    stocksList.add(new Stock("GIN", StockType.PREFERRED, 0.08, 0.02, 0.1));
	    stocksList.add(new Stock("JOE", StockType.COMMON, 0.13, 0.0, 0.25));
	    return stocksList.stream()
	    .collect(Collectors.toMap(Stock::getSymbol, Function.identity()));
	    
	  }

	  
	public Map<String, Stock> getStocks() {
		return stocks;
	}


	public void setStocks(HashMap<String, Stock> stocks) {
		this.stocks = stocks;
	}


	public ArrayList<Trade> getTrades() {
		return trades;
	}

	public void setTrades(ArrayList<Trade> trades) {
		this.trades = trades;
	}

	public boolean recordTrade(Trade trade) throws Exception{
		boolean result = false;
		try{
			result = trades.add(trade);
		}catch(Exception exception){
			throw new Exception("An error has occurred recording a trade in the system's backend.", exception);
		}
		return result;
	}

	public int getTradesNumber() {
		return trades.size();
	}

	public Stock getStockBySymbol(String stockSymbol){
		Stock stock = null;
		try{
			if(stockSymbol!=null){
				stock = stocks.get(stockSymbol);
			}
		}catch(Exception exception){
			System.out.println("An error has occurred recovering the stock object for the stock symbol: "+stockSymbol+"."+  exception);
		}
		return stock;
	}



}
