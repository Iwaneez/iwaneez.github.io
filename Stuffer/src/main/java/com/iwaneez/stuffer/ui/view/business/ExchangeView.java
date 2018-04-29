package com.iwaneez.stuffer.ui.view.business;

import com.iwaneez.stuffer.exchange.service.ExchangeService;
import com.iwaneez.stuffer.ui.component.exchange.PairInfo;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = ExchangeView.VIEW_NAME)
public class ExchangeView extends VerticalLayout implements View {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExchangeView.class);

    public static final String VIEW_NAME = "ExchangeVew";

    ExchangeService exchangeService;


    @Autowired
    public ExchangeView(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostConstruct
    public void init() {
        TabSheet tradingTabSheet = new TabSheet();
        tradingTabSheet.addStyleNames(ValoTheme.TABSHEET_COMPACT_TABBAR, ValoTheme.TABSHEET_FRAMED);
        tradingTabSheet.setSizeFull();

        tradingTabSheet.addTab(new PairInfo(), "Pair Info", VaadinIcons.LINE_CHART);

        addComponent(tradingTabSheet);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}