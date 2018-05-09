package com.iwaneez.stuffer.ui.view.settings;

import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.ui.*;

import java.util.Locale;

public class GeneralSettings extends VerticalLayout implements Localizable {

    private Panel languageSettings;
    private ComboBox<Locale> langComboBox;

    public GeneralSettings() {
        languageSettings = new Panel();

        langComboBox = new ComboBox<>();
        langComboBox.setItems(Localization.supportedLocales);
        langComboBox.setItemCaptionGenerator(Locale::getLanguage);
        langComboBox.setSelectedItem(UI.getCurrent().getLocale());
        langComboBox.setEmptySelectionAllowed(false);
        langComboBox.setTextInputAllowed(false);
        langComboBox.addValueChangeListener(event -> UI.getCurrent().setLocale(event.getValue()));

        FormLayout langForm = new FormLayout(langComboBox);
        langForm.setMargin(true);
        langForm.setSpacing(false);

        languageSettings.setContent(langForm);

        addComponents(languageSettings);

        localize();
        setSizeFull();
    }

    @Override
    public void localize() {
        languageSettings.setCaption(Localization.get("settings.general.language.caption"));
        langComboBox.setCaption(Localization.get("settings.general.language.language"));
    }
}
