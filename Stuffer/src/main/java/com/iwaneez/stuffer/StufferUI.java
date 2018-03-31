package com.iwaneez.stuffer;

import com.iwaneez.stuffer.event.ViewChangedEvent;
import com.iwaneez.stuffer.service.SecurityService;
import com.iwaneez.stuffer.ui.ContentContainer;
import com.iwaneez.stuffer.ui.menu.MenuItem;
import com.iwaneez.stuffer.ui.view.ErrorView;
import com.iwaneez.stuffer.ui.view.LoginView;
import com.iwaneez.stuffer.ui.view.MainView;
import com.iwaneez.stuffer.util.SessionScopedEventBus;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@SpringUI
@Theme("stuffer")
public class StufferUI extends UI {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private SpringNavigator navigator;
    @Autowired
    private ContentContainer contentContainer;

//    private Locale locale;

    private final SessionScopedEventBus sessionScopedEventBus = new SessionScopedEventBus();

    @Override
    protected void init(VaadinRequest request) {
//        this.locale = request.getLocale();

        getPage().setTitle("Vaadin Stuffer App");
        addStyleName(ValoTheme.UI_WITH_MENU);

        Responsive.makeResponsive(this);

        setupNavigator();
        if (!securityService.isLoggedIn()) {
            showLoginView();
        } else {
            showMainView();
        }
    }

    private void showMainView() {
        setContent(new MainView(contentContainer));
    }

    private void showLoginView() {
        setContent(new LoginView(securityService, () -> showMainView()));
    }

    private void setupNavigator() {
        navigator.setErrorView(ErrorView.class);
        navigator.addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
                SessionScopedEventBus.post(new ViewChangedEvent(MenuItem.getByViewName(event.getViewName())));
            }
        });
    }

//    @Override
//    public Locale getLocale() {
//        return locale;
//    }
//
//    @Override
//    public void setLocale(Locale locale) {
//        this.locale = locale;
//    }

    public static SessionScopedEventBus getSessionScopedEventBus() {
        return ((StufferUI) UI.getCurrent()).sessionScopedEventBus;
    }

}