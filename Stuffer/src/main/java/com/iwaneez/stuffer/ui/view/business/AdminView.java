package com.iwaneez.stuffer.ui.view.business;

import com.iwaneez.stuffer.util.Localization;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = AdminView.VIEW_NAME)
public class AdminView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "AdminView";

    @PostConstruct
    public void init() {
        addComponent(new Label(Localization.get("administration.title")));
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}