package com.iwaneez.stuffer;

import com.iwaneez.stuffer.event.BusEvent.ViewChangedEvent;
import com.iwaneez.stuffer.service.SecurityService;
import com.iwaneez.stuffer.ui.ContentContainer;
import com.iwaneez.stuffer.ui.menu.MenuItem;
import com.iwaneez.stuffer.ui.view.AccessDeniedView;
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
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

//@Push(value = PushMode.MANUAL, transport = Transport.LONG_POLLING)
@SpringUI
@Theme("stuffer")
public class StufferUI extends UI {

    private final SecurityService securityService;

    private final SessionScopedEventBus sessionScopedEventBus;

    private final ContentContainer contentContainer;


    @Autowired
    public StufferUI(SecurityService securityService, SpringNavigator navigator, SpringViewProvider viewProvider, ContentContainer contentContainer) {
        this.securityService = securityService;
        this.sessionScopedEventBus = new SessionScopedEventBus();
        this.contentContainer = contentContainer;

        setupNavigator(navigator);
        setupViewProvider(viewProvider);
    }

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Stuffer Trading App");
        addStyleName(ValoTheme.UI_WITH_MENU);
        Responsive.makeResponsive(this);

        setLocale(Localization.getLocale());

        if (!securityService.isLoggedIn()) {
            showLoginView();
        } else {
            showMainView();
        }
    }

    private void setupNavigator(SpringNavigator springNavigator) {
        springNavigator.setErrorView(ErrorView.class);
        springNavigator.addViewChangeListener(new ViewChangeListener() {
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


    private void setupViewProvider(SpringViewProvider springViewProvider) {
        springViewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
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
        Localization.localize(getContent());
    }

    public static SessionScopedEventBus getSessionScopedEventBus() {
        return ((StufferUI) UI.getCurrent()).sessionScopedEventBus;
    }

}