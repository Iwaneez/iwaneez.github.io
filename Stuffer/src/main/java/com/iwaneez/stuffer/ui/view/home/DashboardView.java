package com.iwaneez.stuffer.ui.view.home;

import com.iwaneez.stuffer.exchange.bo.WalletBalance;
import com.iwaneez.stuffer.exchange.bo.WalletBalanceItem;
import com.iwaneez.stuffer.exchange.service.ExchangeService;
import com.iwaneez.stuffer.service.UserService;
import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.Label;
import com.vaadin.ui.components.grid.FooterRow;
import org.knowm.xchange.currency.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringView(name = DashboardView.VIEW_NAME)
public class DashboardView extends VerticalLayout implements View, Localizable {

    private final static Logger LOGGER = LoggerFactory.getLogger(DashboardView.class);

    public static final String VIEW_NAME = "DashboardView";

    private UserService userService;
    private ExchangeService exchangeService;

    private WalletBalance balance;
    private Chart balanceChart;
    private Component balanceTextInfo;

    private Configuration chartConfig;

    // Legend grid columns
    private Grid.Column currencyCol;
    private Grid.Column amountCol;
    private Grid.Column priceCol;
    private Grid.Column totalPriceCol;
    private Grid.Column percentageCol;

    @Autowired
    public DashboardView(UserService userService, ExchangeService exchangeService) {
        this.userService = userService;
        this.exchangeService = exchangeService;
        this.balance = exchangeService.getBalance(userService.getCurrentUser().getActiveProfile(), Currency.BTC);
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
                        new DataSeriesItem(
                                balanceItem.getCurrencyPair().base.getCurrencyCode(),
                                calculatePercentage(balanceItem.getTotalPrice(), walletBalance.getTotalPrice(), false))));

        chartConfig.setSeries(series);
        balancePieChart.drawChart(chartConfig);

        return balancePieChart;
    }

    private Component createBalanceTextInfo(WalletBalance walletBalance) {
        Grid<WalletBalanceItem> grid = new Grid<>();
        grid.setItems(walletBalance.getBalanceItems());
        currencyCol = grid.addColumn(balanceItem -> balanceItem.getCurrencyPair().base.getCurrencyCode());
        amountCol = grid.addColumn(walletBalanceItem -> walletBalanceItem.getAmount().setScale(4, RoundingMode.HALF_UP));
        priceCol = grid.addColumn(walletBalanceItem -> walletBalanceItem.getPrice().setScale(8, RoundingMode.HALF_UP));
        totalPriceCol = grid.addColumn(walletBalanceItem -> walletBalanceItem.getTotalPrice().setScale(8, RoundingMode.HALF_UP));
        percentageCol = grid.addColumn(balanceItem -> calculatePercentage(balanceItem.getTotalPrice(), walletBalance.getTotalPrice(), true));

        FooterRow footer = grid.appendFooterRow();
        footer.getCell(priceCol).setHtml("<span style='vertical-align: middle;'>&Sigma;</span>");
        footer.getCell(totalPriceCol).setText(walletBalance.getTotalPrice().toString());
        footer.getCell(percentageCol).setText("100 %");

        grid.setSizeFull();

        return grid;
    }

    private BigDecimal calculatePercentage(BigDecimal part, BigDecimal aggregate, boolean scaledToHundred) {
        BigDecimal result = part.divide(aggregate, 8, RoundingMode.HALF_UP);
        if (scaledToHundred) {
            result = result.multiply(new BigDecimal("100"));
        }
        return result;
    }

    @Override
    public void localize() {
        if (chartConfig != null) {
            chartConfig.setTitle(Localization.get("dashboard.portfolio.caption"));
        }
        if (balanceTextInfo != null) {
            currencyCol.setCaption(Localization.get("dashboard.portfolio.table.currency"));
            amountCol.setCaption(Localization.get("dashboard.portfolio.table.amount"));
            priceCol.setCaption(Localization.get("dashboard.portfolio.table.price"));
            totalPriceCol.setCaption(Localization.get("dashboard.portfolio.table.totalPrice"));
            percentageCol.setCaption(Localization.get("dashboard.portfolio.table.percentage"));
        }
    }

}