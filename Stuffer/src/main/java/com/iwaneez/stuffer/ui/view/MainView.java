package com.iwaneez.stuffer.ui.view;

import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class MainView extends CssLayout {

    private SpringNavigator navigator;


    public MainView(SpringNavigator navigator, ContentContainer contentContainer) {
        this.navigator = navigator;

        Button menuButton = new Button("Logout", this::logout);
        VerticalLayout appNavigation = new VerticalLayout(menuButton);
        appNavigation.setSizeFull();
        appNavigation.setWidthUndefined();

        HorizontalLayout root = new HorizontalLayout(appNavigation, contentContainer);
        root.setSizeFull();
        root.setExpandRatio(contentContainer, 1);

        setSizeFull();
        addComponent(root);
    }

    private void logout(Button.ClickEvent clickEvent) {
        getUI().getPage().reload();
        getSession().close();
    }
}
