package com.iwaneez.stuffer.exchange.service.impl;

import com.iwaneez.stuffer.exchange.bo.SupportedExchange;
import com.iwaneez.stuffer.exchange.service.ExchangeService;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExchangeServiceImpl.class);

    private Map<SupportedExchange, Exchange> exchanges;


    public ExchangeServiceImpl() {
        exchanges = new HashMap<>();
    }
    
    @Override
    public Exchange getExchange(SupportedExchange supportedExchange) {
        Exchange exchange = exchanges.get(supportedExchange);
        if (exchange == null) {
            exchange = ExchangeFactory.INSTANCE.createExchange(supportedExchange.getExchangeClass());
            exchanges.put(supportedExchange, exchange);
        }
        return exchange;
    }

}
