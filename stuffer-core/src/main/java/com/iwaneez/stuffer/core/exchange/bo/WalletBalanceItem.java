package com.iwaneez.stuffer.core.exchange.bo;

import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;

public class WalletBalanceItem {
    private CurrencyPair currencyPair;
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public WalletBalanceItem(CurrencyPair currencyPair, BigDecimal amount, BigDecimal price) {
        this.currencyPair = currencyPair;
        this.amount = amount;
        this.price = price;
        this.totalPrice = price.multiply(amount);
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
