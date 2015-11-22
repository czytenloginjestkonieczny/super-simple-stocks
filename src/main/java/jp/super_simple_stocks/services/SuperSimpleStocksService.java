package jp.super_simple_stocks.services;

import java.math.BigDecimal;
import java.util.Date;

import jp.super_simple_stocks.model.TradeType;

public interface SuperSimpleStocksService {
	public BigDecimal calculateDividendYield(String stockSymbol);

	public BigDecimal calculatePERatio(String stockSymbol);

	public boolean recordTrade(String stockSymbol, Date timeStamp, BigDecimal quantity, TradeType tradeType,
			BigDecimal price);

	public BigDecimal calculateStockPricesFor15m(String stockSymbol);

	public BigDecimal calculateGBCEAllShareIndex();
}
