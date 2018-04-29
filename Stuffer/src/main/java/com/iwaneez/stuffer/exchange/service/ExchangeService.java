package com.iwaneez.stuffer.exchange.service;

import com.iwaneez.stuffer.exchange.bo.SupportedExchange;
import org.knowm.xchange.Exchange;

public interface ExchangeService {

    Exchange getExchange(SupportedExchange supportedExchange);

}
