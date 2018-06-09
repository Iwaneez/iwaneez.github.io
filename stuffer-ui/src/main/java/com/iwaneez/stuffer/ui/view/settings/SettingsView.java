package com.iwaneez.stuffer.ui.view.settings;

import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.ui.view.settings.exchange.ExchangeSettings;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;


@SpringView(name = SettingsView.VIEW_NAME)
public class SettingsView extends VerticalLayout implements View, Localizable {

    public static final String VIEW_NAME = "SettingsView";

    private TabSheet.Tab exchangeSettings;
    private TabSheet.Tab generalSettings;

    @PostConstruct
    public void init() {
        TabSheet tradingTabSheet = new TabSheet();
        tradingTabSheet.addStyleNames(ValoTheme.TABSHEET_COMPACT_TABBAR, ValoTheme.TABSHEET_FRAMED);
        tradingTabSheet.setSizeFull();

        exchangeSettings = tradingTabSheet.addTab(new ExchangeSettings());
        exchangeSettings.setIcon(VaadinIcons.LINE_CHART);

        generalSettings = tradingTabSheet.addTab(new GeneralSettings());
        generalSettings.setIcon(VaadinIcons.COG_O);

        addComponent(tradingTabSheet);

        localize();
        setSizeFull();
    }

    @Override
    public void localize() {
        exchangeSettings.setCaption(Localization.get("settings.exchange.caption"));
        generalSettings.setCaption(Localization.get("settings.general.caption"));
    }
}