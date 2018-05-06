package com.iwaneez.stuffer.ui.view.business;

import com.iwaneez.stuffer.exchange.service.ExchangeService;
import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.ui.component.exchange.PairInfo;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = ExchangeView.VIEW_NAME)
public class ExchangeView extends VerticalLayout implements View, Localizable {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExchangeView.class);

    public static final String VIEW_NAME = "ExchangeVew";

    private ExchangeService exchangeService;

    private TabSheet.Tab pairInfo;


    @Autowired
    public ExchangeView(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostConstruct
    public void init() {
        TabSheet tradingTabSheet = new TabSheet();
        tradingTabSheet.addStyleNames(ValoTheme.TABSHEET_COMPACT_TABBAR, ValoTheme.TABSHEET_FRAMED);
        tradingTabSheet.setSizeFull();

        pairInfo = tradingTabSheet.addTab(new PairInfo());
        pairInfo.setIcon(VaadinIcons.LINE_CHART);

        addComponent(tradingTabSheet);

        localize();
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Localization.localize(this);
    }

    @Override
    public void localize() {
        pairInfo.setCaption(Localization.get("exchange.pairInfo.caption"));
    }

}