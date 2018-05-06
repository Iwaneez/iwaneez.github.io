package com.iwaneez.stuffer.ui.component.exchange;

import com.google.common.eventbus.Subscribe;
import com.iwaneez.stuffer.event.BusEvent.RefreshExchangeDataEvent;
import com.iwaneez.stuffer.exchange.bo.SupportedExchange;
import com.iwaneez.stuffer.exchange.service.ExchangeService;
import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.ApplicationContextUtils;
import com.iwaneez.stuffer.util.Localization;
import com.iwaneez.stuffer.util.SessionScopedEventBus;
import com.vaadin.data.HasValue;
import com.vaadin.ui.*;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PairInfo extends VerticalLayout implements Localizable {

    private final static Logger LOGGER = LoggerFactory.getLogger(PairInfo.class);

    private static final String DEFAULT_VALUE = "-----";
    private static final String DATE_TIME_FORMAT = "HH:mm:ss dd.MM.yyyy";
    private static final String ZONE_ID = "UTC+02:00";

    private final ExchangeService exchangeService;
    private Exchange selectedExchange;
    private CurrencyPair selectedPair;

    private ComboBox<SupportedExchange> exchangeComboBox;
    private ComboBox<CurrencyPair> currencyPairComboBox;

    private Label timestampLabel;
    private Label pOpenLabel;
    private Label pHighLabel;
    private Label pLowLabel;
    private Label lastLabel;
    private Label askLabel;
    private Label askSizeLabel;
    private Label bidLabel;
    private Label bidSizeLabel;
    private Label volumeLabel;

    private final List<SupportedExchange> supportedExchanges = Arrays.asList(SupportedExchange.values());


    public PairInfo() {
        exchangeService = ApplicationContextUtils.getApplicationContext().getBean(ExchangeService.class);
        SessionScopedEventBus.register(this);

        Component controlPanel = createControlPanel();
        Component dataPanel = createDataPanel();

        addComponents(controlPanel, dataPanel);
        setExpandRatio(dataPanel, 1);

        localize();
        setSizeFull();
    }

    private Component createControlPanel() {
        HorizontalLayout controlPanel = new HorizontalLayout();

        exchangeComboBox = new ComboBox<>();
        exchangeComboBox.setItems(supportedExchanges);
        exchangeComboBox.setItemCaptionGenerator(SupportedExchange::getName);
        exchangeComboBox.addValueChangeListener(this::loadExchangePairs);

        currencyPairComboBox = new ComboBox<>();
        currencyPairComboBox.addValueChangeListener(event -> {
            selectedPair = event.getValue();
            refreshData(null);
        });

//        exchangeComboBox.setSelectedItem(supportedExchanges.get(0));

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

        ohlcForm.addComponents(pOpenLabel, pHighLabel, pLowLabel);
//        ohlcForm.setSizeFull();

        return ohlcForm;
    }

    private Component createAskBidForm() {
        FormLayout askBidForm = new FormLayout();

        lastLabel = new Label(DEFAULT_VALUE);
        lastLabel.setCaption("Last:");

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

        askBidForm.addComponents(lastLabel, askLabel, bidLabel, volumeLabel);
//        askBidForm.setSizeFull();

        return askBidForm;
    }

    private void loadExchangePairs(HasValue.ValueChangeEvent<SupportedExchange> event) {
        selectedExchange = exchangeService.getExchange(event.getValue());
        List<CurrencyPair> pairs = selectedExchange.getExchangeSymbols();
        pairs.sort(Comparator.comparing(CurrencyPair::toString));
        currencyPairComboBox.setItems(pairs);
    }

    @Subscribe
    private void refreshData(RefreshExchangeDataEvent event) {
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

        pOpenLabel.setValue(Optional.ofNullable(dataTicker.getOpen()).map(this::formatPrice).orElse(DEFAULT_VALUE));
        pHighLabel.setValue(Optional.ofNullable(dataTicker.getHigh()).map(this::formatPrice).orElse(DEFAULT_VALUE));
        pLowLabel.setValue(Optional.ofNullable(dataTicker.getLow()).map(this::formatPrice).orElse(DEFAULT_VALUE));

        lastLabel.setValue(Optional.ofNullable(dataTicker.getLast()).map(this::formatPrice).orElse(DEFAULT_VALUE));
        askLabel.setValue(Optional.ofNullable(dataTicker.getAsk()).map(this::formatPrice).orElse(DEFAULT_VALUE));
//        askSizeLabel.setValue(dataTicker.getAskSize() != null ? dataTicker.getAskSize().toString() : DEFAULT_VALUE);
        bidLabel.setValue(Optional.ofNullable(dataTicker.getBid()).map(this::formatPrice).orElse(DEFAULT_VALUE));
//        bidSizeLabel.setValue(dataTicker.getBidSize() != null ? dataTicker.getBidSize().toString() : DEFAULT_VALUE);
        volumeLabel.setValue(Optional.ofNullable(dataTicker.getVolume()).map(BigDecimal::toString).orElse(DEFAULT_VALUE));
    }

    private String formatPrice(BigDecimal bigDecimal) {
        return bigDecimal.toString() + " " + selectedPair.counter;
    }

    private String getDateTimeString() {
        return LocalDateTime.now(ZoneId.of(ZONE_ID)).format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    @Override
    public void localize() {
        exchangeComboBox.setCaption(Localization.get("exchange.pairInfo.exchange"));
        currencyPairComboBox.setCaption(Localization.get("exchange.pairInfo.pair"));
    }
}
