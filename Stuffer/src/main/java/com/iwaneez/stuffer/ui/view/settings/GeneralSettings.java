package com.iwaneez.stuffer.ui.view.settings;

import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.data.HasValue;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.Locale;

public class GeneralSettings extends VerticalLayout implements Localizable {

    private Panel languageSettings;
    private ComboBox<Locale> langComboBox;

    public GeneralSettings() {
        languageSettings = new Panel();
        languageSettings.setContent(new VerticalLayout(langComboBox = createLanguageCombobox()));

        addComponents(languageSettings);

        localize();
        setSizeFull();
    }

    private ComboBox<Locale> createLanguageCombobox() {
        ComboBox<Locale> comboBox = new ComboBox<>();
        comboBox.setItems(Localization.supportedLocales);
        comboBox.setItemCaptionGenerator(Locale::getLanguage);
        comboBox.setSelectedItem(UI.getCurrent().getLocale());
        comboBox.setEmptySelectionAllowed(false);
        comboBox.setTextInputAllowed(false);
        comboBox.addValueChangeListener(this::languageChanged);

        return comboBox;
    }

    private void languageChanged(HasValue.ValueChangeEvent<Locale> event) {
        UI.getCurrent().setLocale(event.getValue());
    }

    @Override
    public void localize() {
        languageSettings.setCaption(Localization.get("settings.general.language.caption"));
        langComboBox.setCaption(Localization.get("settings.general.language.language"));
    }
}
