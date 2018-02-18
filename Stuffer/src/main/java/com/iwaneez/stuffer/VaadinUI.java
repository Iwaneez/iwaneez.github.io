package com.iwaneez.stuffer;

import com.iwaneez.stuffer.persistence.repository.RoleRepository;
import com.iwaneez.stuffer.persistence.repository.UserRepository;
import com.iwaneez.stuffer.service.SecurityService;
import com.iwaneez.stuffer.service.UserService;
import com.iwaneez.stuffer.ui.view.ContentContainer;
import com.iwaneez.stuffer.ui.view.LoginView;
import com.iwaneez.stuffer.ui.view.MainView;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("stuffer")
public class VaadinUI extends UI {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private SpringNavigator navigator;
    @Autowired
    private ContentContainer contentContainer;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Vaadin Stuffer App");
        if (!securityService.isLoggedIn()) {
            showLoginView();
        } else {
            showMainView();
        }
    }

    private void showMainView() {
        setContent(new MainView(navigator, contentContainer));
    }

    private void showLoginView() {
        setContent(new LoginView(securityService, () -> {
//            getUI().getPushConfiguration().setTransport(Transport.WEBSOCKET);
//            getUI().getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
            showMainView();
        }));
    }

}