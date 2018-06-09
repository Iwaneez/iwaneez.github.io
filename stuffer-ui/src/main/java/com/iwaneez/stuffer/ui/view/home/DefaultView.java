package com.iwaneez.stuffer.ui.view.home;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "";

    private Label welcomeMessqge;

    @PostConstruct
    public void init() {
        setSizeFull();
        welcomeMessqge = new Label("Hey, welcome! This is Stuffer the trading app.");

        addComponent(welcomeMessqge);
        setComponentAlignment(welcomeMessqge, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
