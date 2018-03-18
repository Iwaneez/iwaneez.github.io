package com.iwaneez.stuffer.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;

@SpringViewDisplay
public class ContentContainer extends Panel implements ViewDisplay {

    public ContentContainer() {
        setSizeFull();
        setPrimaryStyleName("valo-content");
        addStyleName("v-scrollable");
    }

    @Override
    public void showView(View view) {
        setContent((Component) view);
    }
}
