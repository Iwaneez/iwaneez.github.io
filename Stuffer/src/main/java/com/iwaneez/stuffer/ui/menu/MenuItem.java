package com.iwaneez.stuffer.ui.menu;

import com.iwaneez.stuffer.ui.view.business.AdminView;
import com.iwaneez.stuffer.ui.view.business.SettingsView;
import com.iwaneez.stuffer.ui.view.business.TestView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

public enum MenuItem {

    TEST(TestView.VIEW_NAME, TestView.class, VaadinIcons.HOME, "menu.item.title.test", true),
    SETTINGS(SettingsView.VIEW_NAME, SettingsView.class, VaadinIcons.COGS, "menu.item.title.settings", true),
    ADMINISTRATION(AdminView.VIEW_NAME, AdminView.class, VaadinIcons.DATABASE, "menu.item.title.administration", true);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final String localizationId;
    private final boolean stateful;

    private MenuItem(final String viewName,
                     final Class<? extends View> viewClass, final Resource icon,
                     final String localizationId,
                     final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.localizationId = localizationId;
        this.stateful = stateful;
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

    public boolean isStateful() {
        return stateful;
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