package com.iwaneez.stuffer.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

@SpringViewDisplay
public class ContentContainer extends Panel implements ViewDisplay {

    public ContentContainer() {
        setSizeFull();
    }

    @Override
    public void showView(View view) {
        setContent((Component) view);
    }
}
