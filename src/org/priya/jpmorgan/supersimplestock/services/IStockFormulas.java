package org.priya.jpmorgan.supersimplestock.services;

import java.util.List;

import org.priya.jpmorgan.supersimplestocks.dao.StocksEntityManager;
import org.priya.jpmorgan.supersimplestocks.model.Trade;

public interface IStockFormulas {

	/**
	 * 
	 * @param stocksEntityManager
	 */
	void setStocksEntityManager(StocksEntityManager stocksEntityManager);

	/**
	   * Get dividend yield.
	   *
	   * @param stock
	   * @param marketPrice
	   * @return
	   */
	double ComputeDividendYield(String stock, double marketPrice);

	/**
	   * Get P/E Ratio.
	   *
	   * @param stock
	   * @param marketPrice
	   * @return
	   */
	double ComputePERatio(String stock, double marketPrice);

	/**
	   * Compute the Volume Weighted Stock Price.
	   *
	   * @param trades
	   * @return
	   */
	double ComputeVolumeWeightedStockPrice(List<Trade> trades);

}