package com.iwaneez.stuffer.ui.view.settings;

import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class ExchangeSettings extends VerticalLayout implements Localizable {

    private Panel apiKeySettings;

    public ExchangeSettings() {
        apiKeySettings = new Panel();

//        FormLayout langForm = new FormLayout(langComboBox);
//        langForm.setMargin(true);
//        langForm.setSpacing(false);

//        apiKeySettings.setContent(langForm);

        addComponents(apiKeySettings);

        localize();
        setSizeFull();
    }

    @Override
    public void localize() {
        apiKeySettings.setCaption(Localization.get("settings.exchange.apiKey.caption"));
    }
}
