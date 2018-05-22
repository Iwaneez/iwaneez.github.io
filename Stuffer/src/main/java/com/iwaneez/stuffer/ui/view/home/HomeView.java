package com.iwaneez.stuffer.ui.view.home;

import com.iwaneez.stuffer.exchange.service.ExchangeService;
import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.service.UserService;
import com.iwaneez.stuffer.ui.component.Localizable;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SpringView(name = HomeView.VIEW_NAME)
public class HomeView extends VerticalLayout implements View, Localizable {

    private final static Logger LOGGER = LoggerFactory.getLogger(HomeView.class);

    public static final String VIEW_NAME = "HomeVew";

    private UserService userService;
    private ExchangeService exchangeService;
    private Exchange exchangeInstance;

    private Wallet wallet;

    @Autowired
    public HomeView(UserService userService, ExchangeService exchangeService) {
        this.userService = userService;
        this.exchangeService = exchangeService;
        exchangeInstance = getExchangeInstance(userService.getCurrentUser().getActiveProfile());
        wallet = loadWallet();
    }

    @PostConstruct
    public void init() {
        if (exchangeInstance == null || wallet == null) {
            addComponent(new Label(("No profile set! Please set exchange profile in exchange settings.")));
        } else {
            Component balanceChart = createBalanceChart(wallet);
            Component balanceTextInfo = createBalanceTextInfo(wallet);

            HorizontalLayout content = new HorizontalLayout(balanceChart, balanceTextInfo);
            content.setSizeFull();

            addComponent(content);
        }
        setSizeFull();
        localize();
    }

    private Exchange getExchangeInstance(ExchangeProfile exchangeProfile) {
        if (exchangeProfile == null) {
            return null;
        }

        ExchangeSpecification spec = exchangeService.getExchange(exchangeProfile.getExchange()).getDefaultExchangeSpecification();
        spec.setApiKey(exchangeProfile.getApiKey());
        spec.setSecretKey(exchangeProfile.getSecretKey());

        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    private Wallet loadWallet() {
        if (exchangeInstance == null) {
            return null;
        }
        try {
            return exchangeInstance.getAccountService().getAccountInfo().getWallet();
        } catch (IOException e) {
            LOGGER.error("An error occurred while reading wallet info", e);
            return null;
        }
    }

    private Component createBalanceChart(Wallet wallet) {
        Chart balancePieChart = new Chart(ChartType.PIE);
        Configuration conf = balancePieChart.getConfiguration();

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setCursor(Cursor.POINTER);

        DataLabels dataLabels = new DataLabels();
        dataLabels.setEnabled(true);
        dataLabels.setFormatter("'<b>'+ this.point.name +'</b>: '+ this.percentage +' %'");
        plotOptions.setDataLabels(dataLabels);

        conf.setPlotOptions(plotOptions);

        final BigDecimal[] btcSum = {new BigDecimal(0.0)};
        Map<Currency, BigDecimal> btcValuation = new HashMap<>();
        wallet.getBalances().entrySet().stream()
                .filter(currencyBalanceEntry -> currencyBalanceEntry.getValue().getAvailable().intValue() > 0)
                .forEach(currencyBalanceEntry -> {
                    try {
                        BigDecimal btcPrice = exchangeInstance.getMarketDataService()
                                .getTicker(new CurrencyPair(currencyBalanceEntry.getKey(), Currency.BTC))
                                .getLast();
                        btcSum[0] = btcSum[0].add(btcPrice);
                        btcValuation.put(currencyBalanceEntry.getKey(), btcPrice);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        final DataSeries series = new DataSeries();
        btcValuation.entrySet().stream()
                .forEach(item -> series.add(
                        new DataSeriesItem(item.getKey().getSymbol(), (item.getValue().divide(btcSum[0])).multiply(new BigDecimal(100)))));
        conf.setSeries(series);


        balancePieChart.drawChart(conf);

        return balancePieChart;
    }

    private Component createBalanceTextInfo(Wallet wallet) {
        Label balanceInfo = new Label();
        balanceInfo.setContentMode(ContentMode.HTML);
        balanceInfo.setValue(wallet.getBalances().entrySet().stream()
                .filter(currencyBalanceEntry -> currencyBalanceEntry.getValue().getAvailable().intValue() > 0)
                .map(currencyBalanceEntry -> currencyBalanceEntry.getKey().getSymbol() + ": " + currencyBalanceEntry.getValue().getAvailable())
                .collect(Collectors.joining("<br/>")));

        return balanceInfo;
    }

    @Override
    public void localize() {

    }
}