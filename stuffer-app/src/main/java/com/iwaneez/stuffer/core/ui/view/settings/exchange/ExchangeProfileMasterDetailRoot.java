package com.iwaneez.stuffer.core.ui.view.settings.exchange;

import com.iwaneez.stuffer.core.exchange.service.ExchangeProfileService;
import com.iwaneez.stuffer.core.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.core.ui.component.masterdetail.MasterDetailRoot;
import com.iwaneez.stuffer.core.util.ApplicationContextUtils;

public class ExchangeProfileMasterDetailRoot extends MasterDetailRoot<ExchangeProfile, ExchangeProfileMaster, ExchangeProfileDetail> {

    private ExchangeProfileService exchangeProfileService;

    public ExchangeProfileMasterDetailRoot() {
        super(new ExchangeProfileMaster(), new ExchangeProfileDetail());
        exchangeProfileService = ApplicationContextUtils.getApplicationContext().getBean(ExchangeProfileService.class);
    }

    @Override
    protected void deleteItem(ExchangeProfile item) {
        exchangeProfileService.deleteExchangeProfile(item);
    }
}
