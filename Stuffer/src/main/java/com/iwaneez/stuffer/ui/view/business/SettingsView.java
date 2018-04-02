package com.iwaneez.stuffer.ui.view.business;

import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

import javax.annotation.PostConstruct;
import java.util.Locale;


@SpringView(name = SettingsView.VIEW_NAME)
public class SettingsView extends VerticalLayout implements View, Localizable {

    public static final String VIEW_NAME = "settingsView";

    private Panel languageSettings;
    private ComboBox<Locale> langComboBox;
    private Label settingsText;

    @PostConstruct
    public void init() {
        languageSettings = new Panel();

        langComboBox = new ComboBox<>();
        langComboBox.setItems(Localization.supportedLocales);
        langComboBox.setItemCaptionGenerator(Locale::getLanguage);
        langComboBox.setSelectedItem(UI.getCurrent().getLocale());
        langComboBox.setEmptySelectionAllowed(false);
        langComboBox.setTextInputAllowed(false);
        langComboBox.addValueChangeListener(event -> {
            UI.getCurrent().setLocale(event.getValue());
        });
        settingsText = new Label();

        FormLayout langForm = new FormLayout(langComboBox, settingsText);
        langForm.setMargin(true);
        langForm.setSpacing(false);

        languageSettings.setContent(langForm);

        addComponents(languageSettings);

        localize();
        setSizeFull();
    }

    @Override
    public void localize() {
        languageSettings.setCaption(Localization.get("settings.block.language.name"));
        langComboBox.setCaption(Localization.get("settings.block.language.lang"));
        settingsText.setCaption(Localization.get("settings.block.language.hello"));
    }
}