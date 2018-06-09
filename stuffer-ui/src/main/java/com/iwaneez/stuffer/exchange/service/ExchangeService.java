package com.iwaneez.stuffer.exchange.service;

import com.iwaneez.stuffer.exchange.bo.ExchangeType;
import com.iwaneez.stuffer.exchange.bo.WalletBalance;
import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;

public interface ExchangeService {

    Exchange getExchangeInstance(ExchangeType exchangeType);

    ExchangeSpecification getExchangeSpecification(ExchangeType exchangeType);

    WalletBalance getBalance(ExchangeProfile exchangeProfile, Currency balanceCurrency);

}
