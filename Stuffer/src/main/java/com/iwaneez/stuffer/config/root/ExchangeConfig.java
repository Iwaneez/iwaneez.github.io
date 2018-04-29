package com.iwaneez.stuffer.config.root;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeConfig {

    @Bean
    public Exchange getGenericBinanceExchange() {
        return ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class);
    }
}
