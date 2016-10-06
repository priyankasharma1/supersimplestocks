package org.priya.jpmorgan.supersimplestocks.model;

/**
 * Stock information.
 *
 * @author priya
 */
public class Stock
{
  public final String symbol;
  public final StockType type;
  public final double lastDividend;
  public final double fixedDividend;
  public final double parValue;

  /**
   * Constructor.
   * 
   * @param symbol
   */
  public Stock(String symbol, StockType type, double lastDividend, double fixedDividend, double parValue)
  {
    this.symbol = symbol;
    this.type = type;
    this.lastDividend = lastDividend;
    this.fixedDividend = fixedDividend;
    this.parValue = parValue;
  }

public String getSymbol() {
	return symbol;
}

public StockType getType() {
	return type;
}

public double getLastDividend() {
	return lastDividend;
}

public double getFixedDividend() {
	return fixedDividend;
}

public double getParValue() {
	return parValue;
}
  
}
