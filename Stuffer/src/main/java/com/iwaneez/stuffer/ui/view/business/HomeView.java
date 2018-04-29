package com.iwaneez.stuffer.ui.view.business;

import com.iwaneez.stuffer.util.Localization;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = HomeView.VIEW_NAME)
public class HomeView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "HomeVew";

    @PostConstruct
    public void init() {
        addComponent(new Label(Localization.get("test.title")));
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}