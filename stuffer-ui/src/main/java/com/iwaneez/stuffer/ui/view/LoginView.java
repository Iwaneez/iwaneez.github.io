package com.iwaneez.stuffer.ui.view;

import com.iwaneez.stuffer.service.SecurityService;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;

/**
 * UI content when the user is not logged in yet.
 */

public class LoginView extends CssLayout {

    private static final Logger log = LoggerFactory.getLogger(LoginView.class);

    private SecurityService securityService;
    private Runnable loginSuccessfulCallback;

    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;

    public LoginView(SecurityService securityService, Runnable loginSuccessfulCallback) {
        this.securityService = securityService;
        this.loginSuccessfulCallback = loginSuccessfulCallback;

        addStyleName("login-screen");

        Component loginForm = createLoginForm();

        VerticalLayout centeringLayout = new VerticalLayout(loginForm);
        centeringLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
        centeringLayout.setSizeFull();

        addComponents(centeringLayout);
        setSizeFull();
    }

    private Component createLoginForm() {
        usernameField = new TextField("Username", "admin");
        usernameField.setWidth(15, Unit.EM);
        usernameField.focus();

        passwordField = new PasswordField("Password");
        passwordField.setWidth(15, Unit.EM);

        loginButton = new Button("Login", this::loginClick);
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginButton.setDisableOnClick(true);
        loginButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        // login form
        FormLayout loginForm = new FormLayout(usernameField, passwordField, loginButton);
        loginForm.addStyleName("login-form");
        loginForm.setMargin(false);

        // login panel
        Panel panel = new Panel("Log in", loginForm);
        panel.setIcon(VaadinIcons.USER);
        panel.setWidthUndefined();

        return panel;
    }

    private void loginClick(Button.ClickEvent clickEvent) {
        if (!login(usernameField.getValue(), passwordField.getValue())) {
            showDelayedNotification(new Notification("Login failed",
                    "Please check your usernameField and passwordField and try again.",
                    Notification.Type.HUMANIZED_MESSAGE));
            usernameField.focus();
            loginButton.setEnabled(true);
            return;
        }
        loginSuccessfulCallback.run();
    }

    private boolean login(String username, String password) {
        try {
            securityService.login(username, password);
            return true;
        } catch (AuthenticationException ex) {
            return false;
        }
    }

    private void showDelayedNotification(Notification notification) {
        notification.setDelayMsec(2000);
        notification.show(Page.getCurrent());
    }

}