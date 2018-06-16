package com.iwaneez.stuffer.core.ui.view.settings.exchange;

import com.iwaneez.stuffer.core.exchange.bo.ExchangeType;
import com.iwaneez.stuffer.core.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.core.persistence.repository.ExchangeProfileRepository;
import com.iwaneez.stuffer.core.service.SecurityService;
import com.iwaneez.stuffer.core.ui.component.masterdetail.DetailComponent;
import com.iwaneez.stuffer.core.util.ApplicationContextUtils;
import com.iwaneez.stuffer.core.util.Localization;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;

public class ExchangeProfileDetail extends DetailComponent<ExchangeProfile> {

    private SecurityService securityService;
    private ExchangeProfileRepository exchangeProfileRepository;

    private TextField name;
    private ComboBox<ExchangeType> exchange;
    private TextArea apiKey, secretKey;
    private CheckBox active;

    protected ExchangeProfileDetail() {
        super(ExchangeProfile.class);
        securityService = ApplicationContextUtils.getApplicationContext().getBean(SecurityService.class);
        exchangeProfileRepository = ApplicationContextUtils.getApplicationContext().getBean(ExchangeProfileRepository.class);
    }

    @Override
    protected Component createDetail(Binder<ExchangeProfile> binder) {
        FormLayout form = new FormLayout();

        name = new TextField();
        binder.forField(name).asRequired()
                .bind(ExchangeProfile::getName, ExchangeProfile::setName);

        exchange = new ComboBox();
        exchange.setItems(ExchangeType.values());
        exchange.setTextInputAllowed(false);
        binder.forField(exchange).asRequired()
                .bind(ExchangeProfile::getExchangeType, ExchangeProfile::setExchangeType);

        apiKey = new TextArea();
        binder.forField(apiKey).asRequired()
                .bind(ExchangeProfile::getApiKey, ExchangeProfile::setApiKey);

        secretKey = new TextArea();
        binder.forField(secretKey).asRequired()
                .bind(ExchangeProfile::getSecretKey, ExchangeProfile::setSecretKey);

        form.addComponents(name, exchange, apiKey, secretKey);

        return form;
    }

    @Override
    protected void getFocus() {
        name.focus();
    }

    @Override
    public ExchangeProfile save(ExchangeProfile item) {
        if (item.getOwner() == null) {
            item.setOwner(securityService.getCurrentUser());
        }
        return exchangeProfileRepository.save(item);
    }

    @Override
    public void localize() {
        super.localize();
        name.setCaption(Localization.get("administration.exchangeProfile.name"));
        exchange.setCaption(Localization.get("administration.exchangeProfile.exchange"));
        apiKey.setCaption(Localization.get("administration.exchangeProfile.apiKey"));
        secretKey.setCaption(Localization.get("administration.exchangeProfile.secretKey"));
    }
}
