package jp.super_simple_stocks.services;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import jp.super_simple_stocks.model.Stock;
import jp.super_simple_stocks.model.StockType;
import jp.super_simple_stocks.model.Trade;
import jp.super_simple_stocks.model.TradeType;

public class SuperSimpleStocksServiceImpl implements SuperSimpleStocksService {

	private StockMarketStorage sms;

	public SuperSimpleStocksServiceImpl(StockMarketStorage sms) {
		this.sms = sms;
	}

	@Override
	public BigDecimal calculateDividendYield(String stockSymbol) {
		Stock stock = sms.getStock(stockSymbol);
		if (StockType.COMMON.equals(stock.getStockType())) {
			if (BigDecimal.ZERO.equals(stock.getTickerPrice())) {
				return BigDecimal.ZERO;
			} else {
				return stock.getLastDividend().setScale(2).divide(stock.getTickerPrice(), BigDecimal.ROUND_HALF_UP);
			}
		} else {
			return stock.getFixedDividend().setScale(2).multiply(stock.getParValue()).divide(stock.getTickerPrice(),
					BigDecimal.ROUND_HALF_UP);
		}
	}

	@Override
	public BigDecimal calculatePERatio(String stockSymbol) {
		Stock stock = sms.getStock(stockSymbol);
		if (BigDecimal.ZERO.equals(stock.getLastDividend())) {
			return BigDecimal.ZERO;
		} else {
			return stock.getTickerPrice().setScale(2).divide(stock.getLastDividend(), BigDecimal.ROUND_HALF_UP);
		}
	}

	@Override
	public boolean recordTrade(String stockSymbol, Date timeStamp, BigDecimal quantity, TradeType tradeType,
			BigDecimal price) {
		Stock stock = sms.getStock(stockSymbol);
		Trade trade = new Trade(stock, tradeType, quantity, price, timeStamp);
		return sms.recordTrade(trade);
	}

	@Override
	public BigDecimal calculateStockPricesFor15m(String stockSymbol) {
		List<Trade> trades = sms.getTrades();
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 15);
		Date date = calendar.getTime();
		BigDecimal priceSum = BigDecimal.ZERO;
		BigDecimal quantitySum = BigDecimal.ZERO;
		for (Trade trade : trades) {
			if (!stockSymbol.equals(trade.getStock().getStockSymbol())) {
				continue;
			}
			if (trade.getTimeStamp().after(date)) {
				priceSum = priceSum.add(trade.getPrice().multiply(trade.getQuantity()));
				quantitySum = quantitySum.add(trade.getQuantity());
			}
		}
		if (quantitySum.equals(BigDecimal.ZERO)) {
			return BigDecimal.ZERO;
		} else {
			return priceSum.setScale(2).divide(quantitySum, BigDecimal.ROUND_HALF_UP);
		}
	}

	@Override
	public BigDecimal calculateGBCEAllShareIndex() {
		BigDecimal multiplier = BigDecimal.ZERO;
		int counter = 0;
		for (Stock stock : sms.getStocks()) {
			if (counter == 0) {
				multiplier = calculateStockPricesFor15m(stock.getStockSymbol());
			} else {
				multiplier = multiplier.multiply(calculateStockPricesFor15m(stock.getStockSymbol()));
			}
			counter++;
		}
		double multiplierD = multiplier.doubleValue();
		double result = Math.pow(multiplierD, 1.0 / counter);
		return new BigDecimal(result);
	}
}
