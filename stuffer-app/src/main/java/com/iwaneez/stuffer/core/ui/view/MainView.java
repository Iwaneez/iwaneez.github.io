package com.iwaneez.stuffer.core.ui.view;

import com.iwaneez.stuffer.core.ContentContainer;
import com.iwaneez.stuffer.core.ui.menu.SidebarMenu;
import com.vaadin.ui.HorizontalLayout;

public class MainView extends HorizontalLayout {

    public MainView(ContentContainer contentContainer) {
        setSizeFull();
        setSpacing(false);

        addComponents(new SidebarMenu(), contentContainer);
        setExpandRatio(contentContainer, 1.0f);
    }

}
