package com.iwaneez.stuffer.exchange.bo;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.binance.BinanceExchange;

public enum SupportedExchange {

    BINANCE("Binance", BinanceExchange.class);

    String name;
    Class<? extends BaseExchange> exchangeClass;

    SupportedExchange(String name, Class<? extends BaseExchange> exchangeClass) {
        this.name = name;
        this.exchangeClass = exchangeClass;
    }

    public String getName() {
        return name;
    }

    public Class<? extends BaseExchange> getExchangeClass() {
        return exchangeClass;
    }
}
