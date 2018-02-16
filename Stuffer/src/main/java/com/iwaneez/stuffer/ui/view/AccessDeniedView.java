package com.iwaneez.stuffer.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class AccessDeniedView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Label accessDeniedLabel = new Label("You do not have access to this view.");
        addComponent(accessDeniedLabel);
    }
}
