package org.priya.jpmorgan.supersimplestocks;

import java.util.List;

import org.priya.jpmorgan.supersimplestock.services.IStockFormulas;
import org.priya.jpmorgan.supersimplestocks.dao.StocksEntityManager;
import org.priya.jpmorgan.supersimplestocks.dao.StocksEntityManagerImpl;
import org.priya.jpmorgan.supersimplestocks.model.Stock;
import org.priya.jpmorgan.supersimplestocks.model.Trade;
import org.priya.jpmorgan.supersimplestocks.model.StockType;



/**
 * Stoc Formulas.
 *
 * @author priya
 */
public class StockFormulas implements IStockFormulas
{
	
	/**
	 * 
	 */
	private  StocksEntityManager stocksEntityManager = new StocksEntityManagerImpl();


	/* (non-Javadoc)
	 * @see org.priya.jpmorgan.supersimplestocks.IStockFormulas#setStocksEntityManager(org.priya.jpmorgan.stocks.dao.StocksEntityManager)
	 */
	@Override
	public void setStocksEntityManager(StocksEntityManager stocksEntityManager) {
		this.stocksEntityManager = stocksEntityManager;
	}

  /**
   * Compute dividend from last dividend.
   *
   * @param lastDividend
   * @return
   */
  protected  double ComputeDividendFromLastDividend(double lastDividend)
  {
    return lastDividend;
  }

  /**
   * Compute dividend from fixed dividend.
   * 
   * @param fixedDividend
   * @param parValue
   * @return
   */
  protected  double ComputeDividendFromFixedDividend(double fixedDividend, double parValue)
  {
    return fixedDividend * parValue;
  }

  /**
   * Compute dividend.
   *
   * @param stock
   * @return
   */
  protected  double CommputeDividend(String stockType)
  {
	Stock stock  = stocksEntityManager.getStockBySymbol(stockType);
    final double dividend;
    if (stock.type == StockType.PREFERRED)
    {
      dividend = ComputeDividendFromFixedDividend(stock.fixedDividend, stock.parValue);
    }
    else
    {
      dividend = ComputeDividendFromLastDividend(stock.lastDividend);
    }
    return dividend;
  }

  /**
   * Compute Dividend Yield from dividend.
   *
   * @param dividend
   * @param marketPrice
   * @return
   */
  protected static double ComputeDividendYieldFromDividend(double dividend, double marketPrice)
  {
    return dividend / marketPrice;
  }

  /* (non-Javadoc)
 * @see org.priya.jpmorgan.supersimplestocks.IStockFormulas#ComputeDividendYield(java.lang.String, double)
 */
  @Override
public  double ComputeDividendYield(String stock, double marketPrice)
  {
    return ComputeDividendYieldFromDividend(CommputeDividend(stock), marketPrice);
  }

  /**
   * Compute P/E Ratio.
   *
   * @param marketPrice
   * @param dividend
   * @return
   */
  protected static double ComputePERatio(double marketPrice, double dividend)
  {
    return marketPrice / dividend;
  }

  /* (non-Javadoc)
 * @see org.priya.jpmorgan.supersimplestocks.IStockFormulas#ComputePERatio(java.lang.String, double)
 */
  @Override
public  double ComputePERatio(String stock, double marketPrice)
  {
    return ComputePERatio(marketPrice, CommputeDividend(stock));
  }

  /**
   * Compute Geometric Mean of Trades.
   *
   * @param trades
   * @return
   */
  public static double ComputeGeometricMean(List<Trade> trades)
  {
    double[] prices = new double[trades.size()];
    int iTrade = 0;
    for (Trade trade : trades)
    {
      prices[iTrade] = trade.price;
      iTrade++;
    }
    return ComputeGeometricMean(prices);
  }

  /**
   * Compute Geometric Mean of prices.
   *
   * @param prices
   *          (positive values)
   * @return
   */
  protected static double ComputeGeometricMean(double[] prices)
  {
    double productOfPrices = 1.0;
    for (double price : prices)
    {
      productOfPrices *= price;
    }
    // Prices are positive values so we can use n-root(x) = pow(x, 1/n)
    // Probably better use a 3rd party
    double nRootPower = 1.0 / prices.length;
    return Math.pow(productOfPrices, nRootPower);
  }

  /* (non-Javadoc)
 * @see org.priya.jpmorgan.supersimplestocks.IStockFormulas#ComputeVolumeWeightedStockPrice(java.util.List)
 */
  @Override
public  double ComputeVolumeWeightedStockPrice(List<Trade> trades)
  {
    double allTradesValue = 0.0;
    double totalQuantity = 0.0;
    for (Trade trade : trades)
    {
      allTradesValue += (trade.price * trade.quantity);
      totalQuantity += trade.quantity;
    }
    return allTradesValue / totalQuantity;
  }
}
