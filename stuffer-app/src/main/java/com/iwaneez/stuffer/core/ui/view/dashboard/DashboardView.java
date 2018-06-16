package com.iwaneez.stuffer.core.ui.view.dashboard;

import com.iwaneez.stuffer.core.exchange.bo.WalletBalance;
import com.iwaneez.stuffer.core.exchange.bo.WalletBalanceItem;
import com.iwaneez.stuffer.core.exchange.service.ExchangeService;
import com.iwaneez.stuffer.core.service.SecurityService;
import com.iwaneez.stuffer.core.ui.component.Localizable;
import com.iwaneez.stuffer.core.util.Localization;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardView.class);

    public static final String VIEW_NAME = "DashboardView";

    private static final int INNER_PIE_SIZE = 70;
    private static final String INNER_PIE_COLOR = "#FAFAFA";

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
    public DashboardView(SecurityService securityService, ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
        this.balance = exchangeService.getBalance(securityService.getCurrentUser().getActiveProfile(), Currency.BTC);
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

        Tooltip tooltip = new Tooltip();
        tooltip.setPointFormat("<b>{point.y}</b>");
        chartConfig.setTooltip(tooltip);

        Legend legend = new Legend();
        legend.setLabelFormat("{name}: <b>{percentage:.2f}%</b>");
        chartConfig.setLegend(legend);

        DataSeries innerSeries = new DataSeries();
        innerSeries.add(new DataSeriesItem("TOTAL", walletBalance.getTotalPrice(), new SolidColor(INNER_PIE_COLOR)));
        PlotOptionsPie innerPieOptions = new PlotOptionsPie();
        innerPieOptions.setSize(INNER_PIE_SIZE, Unit.PERCENTAGE);
        innerPieOptions.getDataLabels().setEnabled(false);
        innerSeries.setPlotOptions(innerPieOptions);

        DataSeries outerSeries = new DataSeries("portfolio");
        walletBalance.getBalanceItems().stream()
                .forEach(balanceItem -> outerSeries.add(
                        new DataSeriesItem(
                                balanceItem.getCurrencyPair().base.getCurrencyCode(),
                                balanceItem.getTotalPrice())));
        PlotOptionsPie outerPieOptions = new PlotOptionsPie();
        outerPieOptions.setInnerSize(INNER_PIE_SIZE, Unit.PERCENTAGE);
        outerPieOptions.setCursor(Cursor.POINTER);
        outerPieOptions.setAllowPointSelect(true);
        outerPieOptions.setShowInLegend(true);
        outerPieOptions.getDataLabels().setFormat("<b>{point.name}: </b> {y}");
        outerSeries.setPlotOptions(outerPieOptions);

        chartConfig.setSeries(innerSeries, outerSeries);

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
        footer.getCell(percentageCol).setHtml(
                "<span style='vertical-align: middle; float: left;'>&Sigma;</span>" +
                        "<span style='vertical-align: middle; float: right;'>" + walletBalance.getTotalPrice() + "</span>");

        grid.setStyleName("portfolio-grid");
        grid.setWidth(100, Unit.PERCENTAGE);

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