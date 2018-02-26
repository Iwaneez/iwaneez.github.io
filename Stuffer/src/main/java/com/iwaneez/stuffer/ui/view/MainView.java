package com.iwaneez.stuffer.ui.view;

import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class MainView extends CssLayout {

    private SpringNavigator navigator;


    public MainView(SpringNavigator navigator, ContentContainer contentContainer) {
        this.navigator = navigator;

        Layout menuContainer = createMenuContainer();

        HorizontalLayout root = new HorizontalLayout(menuContainer, contentContainer);
        root.setSizeFull();
        root.setExpandRatio(contentContainer, 1);

        setSizeFull();
        addComponent(root);
    }

    private Layout createMenuContainer() {
        CssLayout menuRoot = new CssLayout();
        menuRoot.setPrimaryStyleName(ValoTheme.MENU_ROOT);

        Label applicationName = new Label("Stuffer Bot");
        applicationName.setPrimaryStyleName(ValoTheme.MENU_TITLE);
//        applicationName.setSizeFull();
        menuRoot.addComponent(applicationName);

        VerticalLayout menu = new VerticalLayout();
        menu.setStyleName(ValoTheme.MENU_PART);
        menu.setSizeFull();
        menuRoot.addComponent(menu);
//        menu.setSizeFull();
//        menu.setWidthUndefined();

        addMenuItem(menu, new Button("Logout", this::logout));

        return menuRoot;
    }

    private void addMenuItem(Layout menu, Component menuItem) {
        menuItem.addStyleNames(ValoTheme.MENU_ITEM, ValoTheme.BUTTON_LINK);
        menu.addComponent(menuItem);
    }

    private void logout(Button.ClickEvent clickEvent) {
        getUI().getPage().reload();
        getSession().close();
    }
}
