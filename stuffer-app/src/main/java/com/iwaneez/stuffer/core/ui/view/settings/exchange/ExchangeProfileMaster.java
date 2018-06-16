package com.iwaneez.stuffer.core.ui.view.settings.exchange;

import com.iwaneez.stuffer.core.exchange.service.ExchangeProfileService;
import com.iwaneez.stuffer.core.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.core.service.SecurityService;
import com.iwaneez.stuffer.core.ui.component.masterdetail.MasterComponent;
import com.iwaneez.stuffer.core.util.ApplicationContextUtils;
import com.iwaneez.stuffer.core.util.CurrentUser;
import com.iwaneez.stuffer.core.util.Localization;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Grid;

public class ExchangeProfileMaster extends MasterComponent<ExchangeProfile> {

    private SecurityService securityService;
    private ExchangeProfileService exchangeProfileService;

    private Grid.Column name, exchange, apiKey, secretKey;

    public ExchangeProfileMaster() {
        securityService = ApplicationContextUtils.getApplicationContext().getBean(SecurityService.class);
        exchangeProfileService = ApplicationContextUtils.getApplicationContext().getBean(ExchangeProfileService.class);
    }

    @Override
    protected Grid<ExchangeProfile> createGrid() {
        String currentUser = CurrentUser.get();
        Grid<ExchangeProfile> grid = new Grid<>(DataProvider.fromCallbacks(
                query -> exchangeProfileService.getUserExchangeProfiles(currentUser).stream(),
                query -> exchangeProfileService.exchangeProfilesCount(currentUser)));
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
