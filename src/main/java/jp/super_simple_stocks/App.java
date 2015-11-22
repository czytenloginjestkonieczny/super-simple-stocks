package jp.super_simple_stocks;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import jp.super_simple_stocks.model.Stock;
import jp.super_simple_stocks.model.StockType;
import jp.super_simple_stocks.model.TradeType;
import jp.super_simple_stocks.services.StockMarketStorage;
import jp.super_simple_stocks.services.StockMarketStorageImpl;
import jp.super_simple_stocks.services.SuperSimpleStocksService;
import jp.super_simple_stocks.services.SuperSimpleStocksServiceImpl;

public class App {
	public static void main(String[] args) {
		StockMarketStorage sms = new StockMarketStorageImpl();
		SuperSimpleStocksService ssss = new SuperSimpleStocksServiceImpl(sms);

		Stock stockTea = new Stock("TEA", StockType.COMMON, BigDecimal.ZERO, null, BigDecimal.valueOf(100));
		Stock stockPop = new Stock("POP", StockType.COMMON, BigDecimal.valueOf(8), null, BigDecimal.valueOf(100));
		Stock stockAle = new Stock("ALE", StockType.COMMON, BigDecimal.valueOf(23), null, BigDecimal.valueOf(60));
		Stock stockGin = new Stock("GIN", StockType.PREFFERED, BigDecimal.valueOf(8), BigDecimal.valueOf(2),
				BigDecimal.valueOf(100));
		Stock stockJoe = new Stock("JOE", StockType.COMMON, BigDecimal.valueOf(13), null, BigDecimal.valueOf(250));

		sms.addStock(stockTea);
		sms.addStock(stockPop);
		sms.addStock(stockAle);
		sms.addStock(stockGin);
		sms.addStock(stockJoe);

		System.out.println("Super Simple Stocks starting");

		Random random = new Random();

		// generate some random data for calculations
		for (Stock stock : sms.getStocks()) {
			for (int i = 0; i < 60; i++) {
				Calendar calendar = new GregorianCalendar();
				calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - i + 1);
				ssss.recordTrade(stock.getStockSymbol(), calendar.getTime(), new BigDecimal(random.nextInt(10)),
						random.nextBoolean() ? TradeType.BUY : TradeType.SELL, new BigDecimal(random.nextInt(100)));
			}

		}

		for (Stock stock : sms.getStocks()) {
			String stockSymbol = stock.getStockSymbol();
			System.out.println("===========================");
			System.out.println("Stock: " + stockSymbol);
			System.out.println("Dividend yield: " + ssss.calculateDividendYield(stockSymbol));
			System.out.println("P/E ratio: " + ssss.calculatePERatio(stockSymbol));
			System.out.println("Stock Prices for 15m: " + ssss.calculateStockPricesFor15m(stockSymbol));
		}

		System.out.println("===========================");
		System.out.println(
				"GBCE all share index: " + ssss.calculateGBCEAllShareIndex().setScale(2, BigDecimal.ROUND_HALF_UP));
	}
}
