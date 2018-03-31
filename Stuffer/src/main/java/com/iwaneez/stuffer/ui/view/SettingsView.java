package com.iwaneez.stuffer.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;


@SpringView(name = SettingsView.VIEW_NAME)
public class SettingsView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "settingsView";

    @PostConstruct
    public void init() {
        addComponent(new Label("tu budu nastavenia jazyka"));
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}