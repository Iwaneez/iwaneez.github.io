package com.iwaneez.stuffer.ui.menu;

import com.iwaneez.stuffer.ui.view.TestView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

public enum MenuItem {

    TEST(TestView.VIEW_NAME, TestView.class, VaadinIcons.HOME, true);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private MenuItem(final String viewName,
                     final Class<? extends View> viewClass, final Resource icon,
                     final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
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