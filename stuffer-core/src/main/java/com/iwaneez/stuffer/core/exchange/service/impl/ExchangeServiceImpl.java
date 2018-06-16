package com.iwaneez.stuffer.core.exchange.service.impl;

import com.iwaneez.stuffer.core.exchange.bo.ExchangeType;
import com.iwaneez.stuffer.core.exchange.bo.WalletBalance;
import com.iwaneez.stuffer.core.exchange.bo.WalletBalanceItem;
import com.iwaneez.stuffer.core.exchange.service.ExchangeService;
import com.iwaneez.stuffer.core.persistence.entity.ExchangeProfile;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeServiceImpl.class);
    // public exchanges without user profiles
    private final Map<ExchangeType, Exchange> exchanges;

    public ExchangeServiceImpl() {
        this.exchanges = new HashMap<>();
        Arrays.stream(ExchangeType.values()).forEach(exchangeType -> {
            Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeType.getExchangeClass());
            exchanges.put(exchangeType, exchange);
        });
    }

    @Override
    public Exchange getExchangeInstance(ExchangeType exchangeType) {
        return exchanges.get(exchangeType);
    }

    @Override
    public ExchangeSpecification getExchangeSpecification(ExchangeType exchangeType) {
        if (exchangeType == null) {
            return null;
        }
        return getExchangeInstance(exchangeType).getDefaultExchangeSpecification();
    }

    @Override
    public WalletBalance getBalance(ExchangeProfile exchangeProfile, Currency balanceCurrency) {
        Exchange exchangeInstance = setupExchangeInstance(exchangeProfile);
        if (exchangeInstance == null) {
            return null;
        }
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

//    public List<Object> getOrders(ExchangeProfile exchangeProfile) {
//        List<Object> orders = new ArrayList<>();
//        Exchange exchangeInstance = setupExchangeInstance(exchangeProfile);
//        if (exchangeInstance == null) {
//            return orders;
//        }
//        try {
//            exchangeInstance.getTradeService().getOpenOrders().getAllOpenOrders().stream().map()
//        } catch (IOException e) {
//            LOGGER.error("something fucked up", e);
//        }
//
//    }


    private Exchange setupExchangeInstance(ExchangeProfile exchangeProfile) {
        if (exchangeProfile == null || exchangeProfile.getExchangeType() == null) {
            return null;
        }

        ExchangeSpecification exchangeSpecification = getExchangeSpecification(exchangeProfile.getExchangeType());
        if (exchangeSpecification == null) {
            return null;
        }
        exchangeSpecification.setApiKey(exchangeProfile.getApiKey());
        exchangeSpecification.setSecretKey(exchangeProfile.getSecretKey());

        return ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    }

}
