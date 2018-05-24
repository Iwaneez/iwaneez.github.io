package com.iwaneez.stuffer.ui.view.home;

import com.iwaneez.stuffer.exchange.service.ExchangeService;
import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.service.UserService;
import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Label;
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
import java.util.ArrayList;
import java.util.List;

@SpringView(name = HomeView.VIEW_NAME)
public class HomeView extends VerticalLayout implements View, Localizable {

    private final static Logger LOGGER = LoggerFactory.getLogger(HomeView.class);

    public static final String VIEW_NAME = "HomeVew";

    private ExchangeService exchangeService;
    private Exchange exchangeInstance;

    private WalletBalance balance;
    private Chart balanceChart;
    private Component balanceTextInfo;

    private Configuration chartConfig;

    @Autowired
    public HomeView(UserService userService, ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
        this.exchangeInstance = getExchangeInstance(userService.getCurrentUser().getActiveProfile());
        this.balance = loadBalance();
    }

    @PostConstruct
    public void init() {
        if (balance == null) {
            addComponent(new Label(("No account selected! Please select an account in exchange settings.")));
        } else if (balance.getBalanceItems().isEmpty()) {
            addComponent(new Label(("You have no funds.")));
        } else {
            balanceChart = createBalanceChart(balance);
            balanceTextInfo = createBalanceTextInfo(balance);

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

    private WalletBalance loadBalance() {
        if (exchangeInstance == null) {
            return null;
        }
        WalletBalance walletBalance = new WalletBalance();
        try {
            // TODO: rename profile to account
            Wallet accountWallet = exchangeInstance.getAccountService().getAccountInfo().getWallet();
            accountWallet.getBalances().entrySet().stream()
                    .filter(currencyBalanceEntry -> currencyBalanceEntry.getValue().getAvailable().intValue() > 0)
                    .forEach(currencyBalanceEntry -> {
                        try {
                            BigDecimal btcPrice = exchangeInstance.getMarketDataService()
                                    .getTicker(new CurrencyPair(currencyBalanceEntry.getKey(), Currency.BTC))
                                    .getLast();
                            walletBalance.addBalanceItem(new WalletBalanceItem(
                                    currencyBalanceEntry.getKey(),
                                    currencyBalanceEntry.getValue().getAvailable(),
                                    btcPrice));
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

    private Chart createBalanceChart(WalletBalance walletBalance) {
        Chart balancePieChart = new Chart(ChartType.PIE);
        chartConfig = balancePieChart.getConfiguration();

        PlotOptionsPie plotOptions = new PlotOptionsPie();

        DataLabels dataLabels = new DataLabels();
        dataLabels.setEnabled(true);
        dataLabels.setFormatter("'<b>'+ this.point.name +'</b>: '+ this.percentage +' %'");
        plotOptions.setDataLabels(dataLabels);
        plotOptions.setCursor(Cursor.POINTER);

        chartConfig.setPlotOptions(plotOptions);

        DataSeries series = new DataSeries();
        walletBalance.getBalanceItems().stream()
                .forEach(balanceItem -> series.add(
                        new DataSeriesItem(balanceItem.getCurrency().getSymbol(), balanceItem.getItemValue().divide(walletBalance.getBitcoinBalance()))));

        chartConfig.setSeries(series);
        balancePieChart.drawChart(chartConfig);

        return balancePieChart;
    }

    private Component createBalanceTextInfo(WalletBalance walletBalance) {
        Grid<WalletBalanceItem> grid = new Grid<>();
        grid.setItems(walletBalance.getBalanceItems());
        grid.addColumn(balanceItem -> balanceItem.getCurrency().getSymbol());
        grid.addColumn(WalletBalanceItem::getAmount);
        grid.addColumn(WalletBalanceItem::getBtcPrice);
        grid.addColumn(balanceItem -> balanceItem.getItemValue().divide(walletBalance.getBitcoinBalance()).multiply(new BigDecimal("100")));

        return grid;
    }

    @Override
    public void localize() {
        if (chartConfig == null) {
            return;
        }
        chartConfig.setTitle(Localization.get("home.portfolio.caption"));
    }

    private static class WalletBalance {
        private List<WalletBalanceItem> balanceItems;
        private BigDecimal bitcoinBalance;

        public WalletBalance() {
            balanceItems = new ArrayList<>();
            bitcoinBalance = BigDecimal.ZERO;
        }

        public List<WalletBalanceItem> getBalanceItems() {
            return balanceItems;
        }

        public BigDecimal getBitcoinBalance() {
            return bitcoinBalance;
        }

        private void addBalanceItem(WalletBalanceItem balanceItem) {
            balanceItems.add(balanceItem);
            bitcoinBalance = bitcoinBalance.add(balanceItem.getItemValue());
        }

    }

    private static class WalletBalanceItem {
        private Currency currency;
        private BigDecimal amount;
        private BigDecimal btcPrice;

        public WalletBalanceItem(Currency currency, BigDecimal amount, BigDecimal btcPrice) {
            this.currency = currency;
            this.amount = amount;
            this.btcPrice = btcPrice;
        }

        public Currency getCurrency() {
            return currency;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public BigDecimal getBtcPrice() {
            return btcPrice;
        }

        private BigDecimal getItemValue() {
            return amount.multiply(btcPrice);
        }
    }

}