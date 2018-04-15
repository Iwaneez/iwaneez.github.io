package com.iwaneez.stuffer;

import com.iwaneez.stuffer.event.ViewChangedEvent;
import com.iwaneez.stuffer.service.SecurityService;
import com.iwaneez.stuffer.ui.ContentContainer;
import com.iwaneez.stuffer.ui.component.Localizable;
import com.iwaneez.stuffer.ui.menu.MenuItem;
import com.iwaneez.stuffer.ui.view.ErrorView;
import com.iwaneez.stuffer.ui.view.LoginView;
import com.iwaneez.stuffer.ui.view.MainView;
import com.iwaneez.stuffer.util.Localization;
import com.iwaneez.stuffer.util.SessionScopedEventBus;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@SpringUI
@Theme("stuffer")
public class StufferUI extends UI {

    private final SecurityService securityService;
    private final SpringNavigator navigator;
    private final ContentContainer contentContainer;

    private final SessionScopedEventBus sessionScopedEventBus;

    @Autowired
    public StufferUI(SecurityService securityService, SpringNavigator navigator, ContentContainer contentContainer) {
        this.securityService = securityService;
        this.navigator = navigator;
        this.contentContainer = contentContainer;
        this.sessionScopedEventBus = new SessionScopedEventBus();
    }

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Vaadin Stuffer App");
        addStyleName(ValoTheme.UI_WITH_MENU);
        Responsive.makeResponsive(this);

        setLocale(Localization.getLocale());

        setupNavigator();
        if (!securityService.isLoggedIn()) {
            showLoginView();
        } else {
            showMainView();
        }
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

    private void showMainView() {
        setContent(new MainView(contentContainer));
    }

    private void showLoginView() {
        setContent(new LoginView(securityService, this::showMainView));
    }

    @Override
    public void setLocale(Locale locale) {
        super.setLocale(locale);
        Localization.setLocaleCookie(locale);
        localize(getContent());
    }

    private void localize(Component component) {
        if (component instanceof Localizable) {
            ((Localizable) component).localize();
        }
        if (component instanceof HasComponents) {
            ((HasComponents) component).iterator().forEachRemaining(this::localize);
        }
    }

    public static SessionScopedEventBus getSessionScopedEventBus() {
        return ((StufferUI) UI.getCurrent()).sessionScopedEventBus;
    }

}