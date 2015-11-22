package jp.super_simple_stocks.model;

import java.math.BigDecimal;
import java.util.Date;

public class Trade {
	private Date timeStamp;
	private Stock stock;
	private TradeType tradeType;
	private BigDecimal quantity;
	private BigDecimal price;

	public Trade(Stock stock, TradeType tradeType, BigDecimal quantity, BigDecimal price) {
		this.timeStamp = new Date();
		this.stock = stock;
		this.tradeType = tradeType;
		this.quantity = quantity;
		this.price = price;
	}

	// for testing purposes
	public Trade(Stock stock, TradeType tradeType, BigDecimal quantity, BigDecimal price, Date timeStamp) {
		this.timeStamp = timeStamp;
		this.stock = stock;
		this.tradeType = tradeType;
		this.quantity = quantity;
		this.price = price;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public Stock getStock() {
		return stock;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}
}
