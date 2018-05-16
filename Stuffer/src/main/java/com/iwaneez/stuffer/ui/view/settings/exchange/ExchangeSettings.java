package com.iwaneez.stuffer.ui.view.settings.exchange;

import com.iwaneez.stuffer.exchange.service.ExchangeProfileService;
import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.service.UserService;
import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.ApplicationContextUtils;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class ExchangeSettings extends VerticalLayout implements Localizable {

    private UserService userService;
    private ExchangeProfileService exchangeProfileService;

    private Panel exchangeProfilesPanel;
    private ComboBox<ExchangeProfile> activeProfileComboBox;
    private Button saveButton;
    private ExchangeProfileMasterDetailRoot masterDetailRoot;
    private Button toggleUserManagement;

    public ExchangeSettings() {
        userService = ApplicationContextUtils.getApplicationContext().getBean(UserService.class);
        exchangeProfileService = ApplicationContextUtils.getApplicationContext().getBean(ExchangeProfileService.class);

        exchangeProfilesPanel = new Panel();
        VerticalLayout content = new VerticalLayout();

        Component activeProfileSelector = createActiveProfileSelector();
        content.addComponent(activeProfileSelector);

        toggleUserManagement = new Button();
        toggleUserManagement.addClickListener(event -> masterDetailRoot.setVisible(!masterDetailRoot.isVisible()));
        toggleUserManagement.addStyleNames(ValoTheme.BUTTON_LINK, "b-link");
        content.addComponent(toggleUserManagement);

        masterDetailRoot = new ExchangeProfileMasterDetailRoot();
        masterDetailRoot.getDetailView().addItemSaveListener(item -> reloadExchangeProfileComboBox());
        masterDetailRoot.setVisible(false);
        content.addComponent(masterDetailRoot);

        exchangeProfilesPanel.setContent(content);
        addComponents(exchangeProfilesPanel);

        localize();
        setSizeFull();
    }

    private Component createActiveProfileSelector() {
        HorizontalLayout profileSelector = new HorizontalLayout();

        activeProfileComboBox = new ComboBox<>();
        activeProfileComboBox.setItems(exchangeProfileService.getCurrentUserExchangeProfiles());
        activeProfileComboBox.setItemCaptionGenerator(ExchangeProfile::getName);
        activeProfileComboBox.setSelectedItem(userService.getCurrentUser().getActiveProfile());
        profileSelector.addComponent(activeProfileComboBox);
        profileSelector.setComponentAlignment(activeProfileComboBox, Alignment.BOTTOM_LEFT);

        saveButton = new Button();
        saveButton.addClickListener(this::saveProfileClicked);
        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        profileSelector.addComponent(saveButton);
        profileSelector.setComponentAlignment(saveButton, Alignment.BOTTOM_LEFT);

        return profileSelector;
    }

    private void saveProfileClicked(Button.ClickEvent event) {
        exchangeProfileService.setCurrentUserActiveProfile(activeProfileComboBox.getValue());
        Notification.show(Localization.get("messages.general.saved"), Notification.Type.HUMANIZED_MESSAGE);
    }

    private void reloadExchangeProfileComboBox() {
        activeProfileComboBox.setItems(exchangeProfileService.getCurrentUserExchangeProfiles());
    }

    @Override
    public void localize() {
        exchangeProfilesPanel.setCaption(Localization.get("settings.exchange.exchangeProfile.caption"));
        activeProfileComboBox.setCaption(Localization.get("settings.exchange.exchangeProfile.activeProfile"));
        saveButton.setCaption(Localization.get("general.button.save"));
        toggleUserManagement.setCaption(Localization.get("settings.exchange.exchangeProfile.exchangeProfileManagement"));
    }
}
