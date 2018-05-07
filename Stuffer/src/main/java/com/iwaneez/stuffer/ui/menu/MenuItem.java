package com.iwaneez.stuffer.ui.menu;

import com.iwaneez.stuffer.persistence.entity.RoleType;
import com.iwaneez.stuffer.ui.view.business.AdminView;
import com.iwaneez.stuffer.ui.view.business.ExchangeView;
import com.iwaneez.stuffer.ui.view.business.HomeView;
import com.iwaneez.stuffer.ui.view.business.SettingsView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

public enum MenuItem {

    HOME(HomeView.VIEW_NAME, HomeView.class, VaadinIcons.HOME, "menu.item.title.home", new RoleType[]{RoleType.USER}),
    TRADING(ExchangeView.VIEW_NAME, ExchangeView.class, VaadinIcons.LINE_CHART, "menu.item.title.exchange", new RoleType[]{RoleType.USER}),
    SETTINGS(SettingsView.VIEW_NAME, SettingsView.class, VaadinIcons.COGS, "menu.item.title.settings", new RoleType[]{RoleType.USER}),
    ADMINISTRATION(AdminView.VIEW_NAME, AdminView.class, VaadinIcons.DATABASE, "menu.item.title.administration", new RoleType[]{RoleType.ADMIN});

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final String localizationId;
    private final RoleType[] mandatoryRoles;

    MenuItem(String viewName, Class<? extends View> viewClass, final Resource icon,
             String localizationId, RoleType[] mandatoryRoles) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.localizationId = localizationId;
        this.mandatoryRoles = mandatoryRoles;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public String getLocalizationId() {
        return localizationId;
    }

    public RoleType[] getMandatoryRoles() {
        return mandatoryRoles;
    }

    public static MenuItem getByViewName(final String viewName) {
        for (MenuItem viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                return viewType;
            }
        }
        return null;
    }

}