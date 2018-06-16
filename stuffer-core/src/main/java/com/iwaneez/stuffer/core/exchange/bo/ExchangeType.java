package com.iwaneez.stuffer.core.exchange.bo;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.kraken.KrakenExchange;

public enum ExchangeType {

    BINANCE("Binance", BinanceExchange.class),
    BITTREX("Bittrex", BittrexExchange.class),
    KRAKEN("Kraken", KrakenExchange.class);

    String name;
    Class<? extends BaseExchange> exchangeClass;

    ExchangeType(String name, Class<? extends BaseExchange> exchangeClass) {
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
