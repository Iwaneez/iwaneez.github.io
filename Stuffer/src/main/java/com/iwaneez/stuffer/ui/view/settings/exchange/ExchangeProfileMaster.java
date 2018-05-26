package com.iwaneez.stuffer.ui.view.settings.exchange;

import com.iwaneez.stuffer.exchange.service.ExchangeProfileService;
import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.ui.component.masterdetail.MasterComponent;
import com.iwaneez.stuffer.util.ApplicationContextUtils;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Grid;

public class ExchangeProfileMaster extends MasterComponent<ExchangeProfile> {

    private ExchangeProfileService exchangeProfileService;

    private Grid.Column name, exchange, apiKey, secretKey;

    public ExchangeProfileMaster() {
        exchangeProfileService = ApplicationContextUtils.getApplicationContext().getBean(ExchangeProfileService.class);
    }

    @Override
    protected Grid<ExchangeProfile> createGrid() {
        Grid<ExchangeProfile> grid = new Grid<>(DataProvider.fromCallbacks(
                query -> exchangeProfileService.getCurrentUserExchangeProfiles().stream(),
                query -> exchangeProfileService.currentUserExchangeProfileCount()));
        name = grid.addColumn(ExchangeProfile::getName);
        exchange = grid.addColumn(ExchangeProfile::getExchangeType);
        apiKey = grid.addColumn(ExchangeProfile::getApiKey);
        secretKey = grid.addColumn(ExchangeProfile::getSecretKey);
        grid.setSizeFull();

        return grid;
    }

    @Override
    public void localize() {
        name.setCaption(Localization.get("administration.exchangeProfile.name"));
        exchange.setCaption(Localization.get("administration.exchangeProfile.exchange"));
        apiKey.setCaption(Localization.get("administration.exchangeProfile.apiKey"));
        secretKey.setCaption(Localization.get("administration.exchangeProfile.secretKey"));
    }
}
