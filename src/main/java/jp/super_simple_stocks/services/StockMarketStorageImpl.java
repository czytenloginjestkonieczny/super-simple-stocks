package jp.super_simple_stocks.services;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jp.super_simple_stocks.model.Stock;
import jp.super_simple_stocks.model.Trade;

public class StockMarketStorageImpl implements StockMarketStorage {
	private Map<String, Stock> stocksMap = new HashMap<String, Stock>();
	private List<Trade> trades = new LinkedList<Trade>();

	@Override
	public void addStock(Stock stock) {
		stocksMap.put(stock.getStockSymbol(), stock);
	}

	@Override
	public List<Stock> getStocks() {
		return new LinkedList<Stock>(stocksMap.values());
	}

	@Override
	public Stock getStock(String stockSymbol) {
		return stocksMap.get(stockSymbol);
	}

	@Override
	public boolean recordTrade(Trade trade) {
		return trades.add(trade);
	}

	@Override
	public List<Trade> getTrades() {
		return trades;
	}
}
