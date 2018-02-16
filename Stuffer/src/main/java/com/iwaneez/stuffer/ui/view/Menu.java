package com.iwaneez.stuffer.ui.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class Menu extends CssLayout {

    public Menu() {
        setSizeFull();
        setPrimaryStyleName(ValoTheme.MENU_ROOT);
    }

    public void addMenu(Component menu) {
        menu.addStyleName(ValoTheme.MENU_PART);
        addComponent(menu);
    }

}