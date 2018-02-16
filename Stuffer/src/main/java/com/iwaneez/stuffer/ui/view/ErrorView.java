package com.iwaneez.stuffer.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ErrorView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Label accessDeniedLabel = new Label("Selected view does not exist.");
        addComponent(accessDeniedLabel);
    }
}
