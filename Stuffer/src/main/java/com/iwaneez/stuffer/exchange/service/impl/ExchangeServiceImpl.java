package com.iwaneez.stuffer.exchange.service.impl;

import com.iwaneez.stuffer.exchange.bo.ExchangeType;
import com.iwaneez.stuffer.exchange.bo.WalletBalance;
import com.iwaneez.stuffer.exchange.bo.WalletBalanceItem;
import com.iwaneez.stuffer.exchange.service.ExchangeService;
import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExchangeServiceImpl.class);
    // public exchanges without user profiles
    private final Map<ExchangeType, Exchange> exchanges;

    public ExchangeServiceImpl() {
        this.exchanges = new HashMap<>();
    }

    @Override
    public Exchange getExchange(ExchangeType exchangeType) {
        Exchange exchange = exchanges.get(exchangeType);
        if (exchange == null) {
            exchange = ExchangeFactory.INSTANCE.createExchange(exchangeType.getExchangeClass());
            exchanges.put(exchangeType, exchange);
        }
        return exchange;
    }

    @Override
    public ExchangeSpecification getExchangeSpecification(ExchangeType exchangeType) {
        if (exchangeType == null) {
            return null;
        }
        return getExchange(exchangeType).getDefaultExchangeSpecification();
    }

    @Override
    public WalletBalance getBalance(ExchangeProfile exchangeProfile, Currency balanceCurrency) {
        if (exchangeProfile == null || exchangeProfile.getExchangeType() == null) {
            return null;
        }

        ExchangeSpecification exchangeSpecification = getExchangeSpecification(exchangeProfile.getExchangeType());
        if (exchangeSpecification == null) {
            return null;
        }
        exchangeSpecification.setApiKey(exchangeProfile.getApiKey());
        exchangeSpecification.setSecretKey(exchangeProfile.getSecretKey());

        Exchange exchangeInstance = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
        WalletBalance walletBalance = new WalletBalance(balanceCurrency);
        try {
            Wallet accountWallet = exchangeInstance.getAccountService().getAccountInfo().getWallet();
            accountWallet.getBalances().entrySet().stream()
                    .filter(currencyBalanceEntry -> currencyBalanceEntry.getValue().getAvailable().intValue() > 0)
                    .forEach(currencyBalanceEntry -> {
                        try {
                            CurrencyPair currencyPair = new CurrencyPair(currencyBalanceEntry.getKey(), balanceCurrency);
                            BigDecimal itemPrice = exchangeInstance.getMarketDataService().getTicker(currencyPair).getLast();
                            walletBalance.addBalanceItem(new WalletBalanceItem(
                                    currencyPair,
                                    currencyBalanceEntry.getValue().getAvailable(),
                                    itemPrice));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            LOGGER.error("An error occurred while reading wallet info", e);
        } finally {
            return walletBalance;
        }
    }

}
