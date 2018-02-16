package com.iwaneez.stuffer.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = MainView.VIEW_NAME)
public class MainView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "";

//    @Autowired
//    EventBus.ViewEventBus eventBus;

    @PostConstruct
    void init() {
        Button clickMeButton = new Button("ClickMe", clickEvent -> Notification.show("Ya clicked!"));

        addComponent(clickMeButton);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
