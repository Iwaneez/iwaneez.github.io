package com.iwaneez.stuffer.ui.view;

import com.iwaneez.stuffer.ui.ContentContainer;
import com.iwaneez.stuffer.ui.menu.SidebarMenu;
import com.vaadin.ui.HorizontalLayout;

public class MainView extends HorizontalLayout {

    public MainView(ContentContainer contentContainer) {
        setSizeFull();
        setSpacing(false);

        addComponents(new SidebarMenu(), contentContainer);
        setExpandRatio(contentContainer, 1.0f);
    }

}
