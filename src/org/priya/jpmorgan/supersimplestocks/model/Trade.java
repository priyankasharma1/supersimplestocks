package org.priya.jpmorgan.supersimplestocks.model;

/**
 * Trade information for a given stock.
 *
 * @author GBesancon
 */
public class Trade
{
  public final Stock stock;
  public final TradeIndicator indicator;
  public final double price;
  public final int quantity;
  public final long timestamp;

  /**
   * Constructor.
   * 
   * @param stock
   * @param indicator
   * @param price
   * @param quantity
   */
  public Trade(Stock stock, TradeIndicator indicator, double price, int quantity, long timestamp)
  {
    this.stock = stock;
    this.indicator = indicator;
    this.price = price;
    this.quantity = quantity;
    this.timestamp = timestamp;
  }
  
  public Stock getStock(){
	  return stock;
  }

  public double getPrice() {
		return price;
	}
	
  public int getQuantity() {
		return quantity;
	}
  
}
