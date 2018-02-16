package com.iwaneez.stuffer;

import com.iwaneez.stuffer.ui.view.ContentContainer;
import com.iwaneez.stuffer.ui.view.ErrorView;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("stuffer")
public class VaadinUI extends UI {

    @Autowired
    private SpringNavigator navigator;
    @Autowired
    private ContentContainer contentContainer;

    @Override
    protected void init(VaadinRequest request) {
        HorizontalLayout root = new HorizontalLayout();
        root.setSizeFull();
        setContent(root);

//        viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
        navigator.setErrorView(ErrorView.class);

        VerticalLayout appNavigation = new VerticalLayout();

        root.addComponents(appNavigation, contentContainer);
        root.setExpandRatio(contentContainer, 1);

//        viewContainer.setPrimaryStyleName("valo-content");
//        viewContainer.addStyleName("v-scrollable");
//        contentArea.setSizeFull();
//
//        setc
//        addComponents(menuArea, contentArea);
//        setExpandRatio(contentArea, 1);
//        setContent(new Button("Click me", e -> Notification.show("Hello Spring+Vaadin user!")));
    }

}