package com.iwaneez.stuffer.core.ui.view.settings.exchange;

import com.iwaneez.stuffer.core.exchange.service.ExchangeProfileService;
import com.iwaneez.stuffer.core.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.core.service.SecurityService;
import com.iwaneez.stuffer.core.ui.component.Localizable;
import com.iwaneez.stuffer.core.util.ApplicationContextUtils;
import com.iwaneez.stuffer.core.util.CurrentUser;
import com.iwaneez.stuffer.core.util.Localization;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class ExchangeSettings extends VerticalLayout implements Localizable {

    private SecurityService securityService;
    private ExchangeProfileService exchangeProfileService;

    private Panel exchangeProfilesPanel;
    private ComboBox<ExchangeProfile> activeProfileComboBox;
    private Button saveButton;
    private ExchangeProfileMasterDetailRoot masterDetailRoot;
    private Button toggleUserManagement;

    public ExchangeSettings() {
        securityService = ApplicationContextUtils.getApplicationContext().getBean(SecurityService.class);
        exchangeProfileService = ApplicationContextUtils.getApplicationContext().getBean(ExchangeProfileService.class);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();

        Component activeProfileSelector = createActiveProfileSelector();
        content.addComponent(activeProfileSelector);

        toggleUserManagement = new Button();
        toggleUserManagement.addClickListener(event -> masterDetailRoot.setVisible(!masterDetailRoot.isVisible()));
        toggleUserManagement.addStyleNames(ValoTheme.BUTTON_LINK, "b-link", "no-padding");
        content.addComponent(toggleUserManagement);

        masterDetailRoot = new ExchangeProfileMasterDetailRoot();
        masterDetailRoot.getDetailView().addItemSaveListener(item -> reloadExchangeProfileComboBox());
        masterDetailRoot.setVisible(false);
        content.addComponent(masterDetailRoot);

        exchangeProfilesPanel = new Panel(content);

        addComponents(exchangeProfilesPanel);

        setSizeFull();
        localize();
    }

    private Component createActiveProfileSelector() {
        HorizontalLayout profileSelector = new HorizontalLayout();

        activeProfileComboBox = new ComboBox<>();
        activeProfileComboBox.setItems(exchangeProfileService.getUserExchangeProfiles(CurrentUser.get()));
        activeProfileComboBox.setItemCaptionGenerator(ExchangeProfile::getName);
        activeProfileComboBox.setSelectedItem(securityService.getCurrentUser().getActiveProfile());
        activeProfileComboBox.setTextInputAllowed(false);
        profileSelector.addComponent(activeProfileComboBox);
        profileSelector.setComponentAlignment(activeProfileComboBox, Alignment.BOTTOM_LEFT);

        saveButton = new Button();
        saveButton.addClickListener(this::saveProfileClick);
        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        profileSelector.addComponent(saveButton);
        profileSelector.setComponentAlignment(saveButton, Alignment.BOTTOM_LEFT);

        return profileSelector;
    }

    private void saveProfileClick(Button.ClickEvent event) {
        exchangeProfileService.setUserActiveProfile(securityService.getCurrentUser(), activeProfileComboBox.getValue());
        Notification.show(Localization.get("messages.general.saved"), Notification.Type.HUMANIZED_MESSAGE);
    }

    private void reloadExchangeProfileComboBox() {
        activeProfileComboBox.setItems(exchangeProfileService.getUserExchangeProfiles(CurrentUser.get()));
    }

    @Override
    public void localize() {
        exchangeProfilesPanel.setCaption(Localization.get("settings.exchange.exchangeProfile.caption"));
        activeProfileComboBox.setCaption(Localization.get("settings.exchange.exchangeProfile.activeProfile"));
        saveButton.setCaption(Localization.get("general.button.save"));
        toggleUserManagement.setCaption(Localization.get("settings.exchange.exchangeProfile.exchangeProfileManagement"));
    }
}
