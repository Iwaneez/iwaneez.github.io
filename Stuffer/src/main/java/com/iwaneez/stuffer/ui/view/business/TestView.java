package com.iwaneez.stuffer.ui.view.business;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = TestView.VIEW_NAME)
public class TestView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "testView";

    @PostConstruct
    public void init() {
        addComponent(new Label("toto je test"));
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}