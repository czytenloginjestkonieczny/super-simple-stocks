package jp.super_simple_stocks.services;

import java.util.List;

import jp.super_simple_stocks.model.Stock;
import jp.super_simple_stocks.model.Trade;

public interface StockMarketStorage {
	public void addStock(Stock stock);

	public List<Stock> getStocks();

	public Stock getStock(String stockSymbol);

	public boolean recordTrade(Trade trade);

	public List<Trade> getTrades();

}
