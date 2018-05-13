package com.iwaneez.stuffer.ui.view.settings.exchange;

import com.iwaneez.stuffer.exchange.bo.SupportedExchange;
import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.persistence.repository.ExchangeProfileRepository;
import com.iwaneez.stuffer.service.UserService;
import com.iwaneez.stuffer.ui.component.masterdetail.DetailComponent;
import com.iwaneez.stuffer.util.ApplicationContextUtils;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;

public class ExchangeProfileDetail extends DetailComponent<ExchangeProfile> {

    private UserService userService;
    private ExchangeProfileRepository exchangeProfileRepository;

    private TextField name;
    private ComboBox<SupportedExchange> exchange;
    private TextArea apiKey, secretKey;
    private CheckBox active;

    protected ExchangeProfileDetail() {
        super(ExchangeProfile.class);
        userService = ApplicationContextUtils.getApplicationContext().getBean(UserService.class);
        exchangeProfileRepository = ApplicationContextUtils.getApplicationContext().getBean(ExchangeProfileRepository.class);
    }

    @Override
    protected Component createDetail(Binder<ExchangeProfile> binder) {
        FormLayout form = new FormLayout();

        name = new TextField();
        binder.forField(name).asRequired()
                .bind(ExchangeProfile::getName, ExchangeProfile::setName);

        exchange = new ComboBox();
        exchange.setItems(SupportedExchange.values());
        binder.forField(exchange).asRequired()
                .bind(ExchangeProfile::getExchange, ExchangeProfile::setExchange);

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
        // TODO: replace this hack wit correctly iniialized beans with all needed values
        if (item.getOwner() == null) {
            item.setOwner(userService.getCurrentUser());
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
