package com.iwaneez.stuffer.ui.menu;

import com.google.common.eventbus.Subscribe;
import com.iwaneez.stuffer.event.BusEvent;
import com.iwaneez.stuffer.persistence.entity.RoleType;
import com.iwaneez.stuffer.service.SecurityService;
import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.util.ApplicationContextUtils;
import com.iwaneez.stuffer.util.Localization;
import com.iwaneez.stuffer.util.SessionScopedEventBus;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class SidebarMenu extends CustomComponent {

    private static final String STYLE_VISIBLE = "valo-menu-visible";

    private final SecurityService securityService;


    public SidebarMenu() {
        SessionScopedEventBus.register(this);
        securityService = ApplicationContextUtils.getApplicationContext().getBean(SecurityService.class);

        setPrimaryStyleName(ValoTheme.MENU_ROOT);
        setSizeUndefined();

        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
        CssLayout menuContent = new CssLayout();
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleNames("sidebar", "no-vertical-drag-hints", "no-horizontal-drag-hints");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildMenuItems());
        menuContent.addComponent(buildToggleButton());

        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label("App <strong>Stuffer Bot</strong>", ContentMode.HTML);
        logo.setSizeUndefined();

        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName(ValoTheme.MENU_TITLE);
        logoWrapper.setSpacing(false);

        return logoWrapper;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");

        for (MenuItem menuItem : MenuItem.values()) {
            if (!isVisible(menuItem)) {
                continue;
            }
            menuItemsLayout.addComponent(new MenuItemButton(menuItem));
        }
        menuItemsLayout.addComponent(buildLogoutButton());

        return menuItemsLayout;
    }

    private boolean isVisible(MenuItem menuItem) {
        for (RoleType role : menuItem.getMandatoryRoles()) {
            if (!securityService.hasRole(role)) {
                return false;
            }
        }
        return true;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", event -> {
            if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                getCompositionRoot().removeStyleName(STYLE_VISIBLE);
            } else {
                getCompositionRoot().addStyleName(STYLE_VISIBLE);
            }
        });
        valoMenuToggleButton.setIcon(VaadinIcons.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        return valoMenuToggleButton;
    }

    private Component buildLogoutButton() {
        Button logoutButton = new Button("Logout", event -> {
            getUI().getPage().reload();
            getSession().close();
        });
        logoutButton.setIcon(VaadinIcons.SIGN_OUT);
        logoutButton.addStyleNames(ValoTheme.BUTTON_LINK, "b-link", "logout");

        return logoutButton;
    }

    private static class MenuItemButton extends Button implements Localizable {

        private static final String STYLE_SELECTED = "selected";

        private final MenuItem menuItem;

        public MenuItemButton(MenuItem menuItem) {
            this.menuItem = menuItem;
            SessionScopedEventBus.register(this);

            setPrimaryStyleName(ValoTheme.MENU_ITEM);
            setIcon(menuItem.getIcon());
            addClickListener(event -> UI.getCurrent().getNavigator().navigateTo(menuItem.getViewName()));

            localize();
        }

        @Subscribe
        public void postViewChange(BusEvent.ViewChangedEvent viewChangedEvent) {
            removeStyleName(STYLE_SELECTED);
            if (viewChangedEvent.getMenuItem() == menuItem) {
                addStyleName(STYLE_SELECTED);
            }
        }

        @Override
        public void localize() {
            setCaption(Localization.get(menuItem.getLocalizationId()));
        }
    }
}
