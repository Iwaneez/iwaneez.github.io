package com.iwaneez.stuffer.ui.component.exchange;

import com.google.common.eventbus.Subscribe;
import com.iwaneez.stuffer.event.RefreshExchangeDataEvent;
import com.iwaneez.stuffer.exchange.bo.SupportedExchange;
import com.iwaneez.stuffer.exchange.service.ExchangeService;
import com.iwaneez.stuffer.util.ApplicationContextUtils;
import com.iwaneez.stuffer.util.SessionScopedEventBus;
import com.vaadin.ui.*;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class PairInfo extends VerticalLayout {

    private final static Logger LOGGER = LoggerFactory.getLogger(PairInfo.class);

    private static final String DEFAULT_VALUE = "---";

    private final ExchangeService exchangeService;
    private Exchange selectedExchange;
    private CurrencyPair selectedPair;

    private Label timestampLabel;
    private Label pOpenLabel;
    private Label pHighLabel;
    private Label pLowLabel;
    private Label pCLoseLabel ;
    private Label volumeLabel;
    private Label askLabel;
    private Label askSizeLabel;
    private Label bidLabel;
    private Label bidSizeLabel;

    private final List<SupportedExchange> supportedExchanges = Arrays.asList(SupportedExchange.values());


    public PairInfo() {
        exchangeService = ApplicationContextUtils.getApplicationContext().getBean(ExchangeService.class);
        SessionScopedEventBus.register(this);

        Component controlPanel = createControlPanel();
        Component dataPanel = createDataPanel();

        addComponents(controlPanel, dataPanel);
        setExpandRatio(dataPanel, 1);

        setSizeFull();
    }

    private Component createControlPanel() {
        HorizontalLayout controlPanel = new HorizontalLayout();

        ComboBox<CurrencyPair> currencyPairComboBox = new ComboBox<>("Pair");
        currencyPairComboBox.addValueChangeListener(event -> {
            selectedPair = event.getValue();
            refreshData(null);
        });

        ComboBox<SupportedExchange> exchangeComboBox = new ComboBox<>("Exchange", supportedExchanges);
        exchangeComboBox.addValueChangeListener(event -> {
            selectedExchange = exchangeService.getExchange(event.getValue());
            currencyPairComboBox.setItems(selectedExchange.getExchangeSymbols());
        });
        exchangeComboBox.setSelectedItem(supportedExchanges.get(0));

        controlPanel.addComponents(exchangeComboBox, currencyPairComboBox);

        return controlPanel;
    }

    private Component createDataPanel() {
        VerticalLayout dataPanel = new VerticalLayout();
        dataPanel.setSizeFull();

        timestampLabel = new Label(getDateTimeString());
        timestampLabel.setCaption("Date:");

        Component ohlcForm = createOhlcForm();
        Component askBidForm = createAskBidForm();

        HorizontalLayout horizontalLayout = new HorizontalLayout(ohlcForm, askBidForm);
        horizontalLayout.setSizeFull();

        dataPanel.addComponents(timestampLabel, horizontalLayout);
        dataPanel.setExpandRatio(horizontalLayout, 1);

        return dataPanel;
    }

    private Component createOhlcForm() {
        FormLayout ohlcForm = new FormLayout();

        pOpenLabel = new Label(DEFAULT_VALUE);
        pOpenLabel.setCaption("Open:");

        pHighLabel = new Label(DEFAULT_VALUE);
        pHighLabel.setCaption("High:");

        pLowLabel = new Label(DEFAULT_VALUE);
        pLowLabel.setCaption("Low:");

        pCLoseLabel = new Label(DEFAULT_VALUE);
        pCLoseLabel.setCaption("Close:");

        ohlcForm.addComponents(pOpenLabel, pHighLabel, pLowLabel, pCLoseLabel);
//        ohlcForm.setSizeFull();

        return ohlcForm;
    }

    private Component createAskBidForm() {
        FormLayout askBidForm = new FormLayout();

        askLabel = new Label(DEFAULT_VALUE);
        askLabel.setCaption("Ask:");

//        askSizeLabel = new Label();
//        askSizeLabel.setCaption("Ask size");
//        dataPanel.addComponent(askSizeLabel);

        bidLabel = new Label(DEFAULT_VALUE);
        bidLabel.setCaption("Bid:");

//        bidSizeLabel = new Label();
//        bidSizeLabel.setCaption("Bid size");
//        dataPanel.addComponent(bidSizeLabel);

        volumeLabel = new Label(DEFAULT_VALUE);
        volumeLabel.setCaption("Volume:");

        askBidForm.addComponents(askLabel, bidLabel, volumeLabel);
//        askBidForm.setSizeFull();

        return askBidForm;
    }

    @Subscribe
    public void refreshData(RefreshExchangeDataEvent event) {
        if (selectedExchange == null) {
            return;
        }
        if (selectedPair == null) {
            return;
        }
        Ticker dataTicker;
        try {
            dataTicker = selectedExchange.getMarketDataService().getTicker(selectedPair);
        } catch (IOException e) {
            LOGGER.error("An exception occured while reading pair data", e);
            return;
        }
        reloadDataPanel(dataTicker);
    }

    private void reloadDataPanel(Ticker dataTicker) {
        timestampLabel.setValue(getDateTimeString());

        pOpenLabel.setValue(dataTicker.getOpen() != null ? dataTicker.getOpen().toString() : DEFAULT_VALUE);
        pHighLabel.setValue(dataTicker.getHigh() != null ? dataTicker.getHigh().toString() : DEFAULT_VALUE);
        pLowLabel.setValue(dataTicker.getLow() != null ? dataTicker.getLow().toString() : DEFAULT_VALUE);
        pCLoseLabel.setValue(dataTicker.getLast() != null ? dataTicker.getLast().toString() : DEFAULT_VALUE);

        askLabel.setValue(dataTicker.getAsk() != null ? dataTicker.getAsk().toString() : DEFAULT_VALUE);
//        askSizeLabel.setValue(dataTicker.getAskSize() != null ? dataTicker.getAskSize().toString() : DEFAULT_VALUE);
        bidLabel.setValue(dataTicker.getBid() != null ? dataTicker.getBid().toString() : DEFAULT_VALUE);
//        bidSizeLabel.setValue(dataTicker.getBidSize() != null ? dataTicker.getBidSize().toString() : DEFAULT_VALUE);
        volumeLabel.setValue(dataTicker.getVolume() != null ? dataTicker.getVolume().toString() : DEFAULT_VALUE);
    }

    private String getDateTimeString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }
}
