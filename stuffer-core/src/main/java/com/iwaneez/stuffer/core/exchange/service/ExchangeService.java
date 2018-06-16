package com.iwaneez.stuffer.core.exchange.service;

import com.iwaneez.stuffer.core.exchange.bo.ExchangeType;
import com.iwaneez.stuffer.core.exchange.bo.WalletBalance;
import com.iwaneez.stuffer.core.persistence.entity.ExchangeProfile;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;

public interface ExchangeService {

    Exchange getExchangeInstance(ExchangeType exchangeType);

    ExchangeSpecification getExchangeSpecification(ExchangeType exchangeType);

    WalletBalance getBalance(ExchangeProfile exchangeProfile, Currency balanceCurrency);

}
