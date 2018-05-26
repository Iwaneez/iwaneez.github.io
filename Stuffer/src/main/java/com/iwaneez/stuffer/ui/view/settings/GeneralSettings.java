package com.iwaneez.stuffer.ui.view.settings;

import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Locale;

public class GeneralSettings extends VerticalLayout implements Localizable {

    private Panel languageSettings;
    private ComboBox<Locale> langComboBox;
    private Button saveButton;

    public GeneralSettings() {
        languageSettings = new Panel();
        languageSettings.setContent(new VerticalLayout(createLanguageCombobox()));

        addComponents(languageSettings);

        localize();
        setSizeFull();
    }

    private Component createLanguageCombobox() {
        HorizontalLayout languageSelector = new HorizontalLayout();

        langComboBox = new ComboBox<>();
        langComboBox.setItems(Localization.supportedLocales);
        langComboBox.setItemCaptionGenerator(Locale::getLanguage);
        langComboBox.setSelectedItem(UI.getCurrent().getLocale());
        langComboBox.setEmptySelectionAllowed(false);
        langComboBox.setTextInputAllowed(false);
        languageSelector.addComponent(langComboBox);
        languageSelector.setComponentAlignment(langComboBox, Alignment.BOTTOM_LEFT);

        saveButton = new Button();
        saveButton.addClickListener(this::saveLanguageClicked);
        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        languageSelector.addComponent(saveButton);
        languageSelector.setComponentAlignment(saveButton, Alignment.BOTTOM_LEFT);

        return languageSelector;
    }

    private void saveLanguageClicked(Button.ClickEvent event) {
        UI.getCurrent().setLocale(langComboBox.getValue());
    }

    @Override
    public void localize() {
        languageSettings.setCaption(Localization.get("settings.general.language.caption"));
        langComboBox.setCaption(Localization.get("settings.general.language.language"));
        saveButton.setCaption(Localization.get("general.button.save"));
    }
}
