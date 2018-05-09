package com.iwaneez.stuffer.ui.view.administration;

import com.iwaneez.stuffer.event.BusEvent;
import com.iwaneez.stuffer.persistence.entity.User;
import com.iwaneez.stuffer.service.UserService;
import com.iwaneez.stuffer.ui.component.masterdetail.DetailComponent;
import com.iwaneez.stuffer.util.ApplicationContextUtils;
import com.iwaneez.stuffer.util.Localization;
import com.iwaneez.stuffer.util.SessionScopedEventBus;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;

public class UserDetailComponent extends DetailComponent<User> {

    private FormLayout formLayout;
    private Binder<User> binder;

    private TextField username;
    private PasswordField password;
    private PasswordField passwordAgain;

    private UserService userService;


    public UserDetailComponent() {
        super();
        userService = ApplicationContextUtils.getApplicationContext().getBean(UserService.class);
    }

    @Override
    protected Component createDetail(Component buttonControls) {
        formLayout = new FormLayout();
        binder = new Binder<>(User.class);

        username = new TextField();
        binder.forField(username).asRequired()
                .bind(User::getUsername, User::setUsername);

        password = new PasswordField();
        binder.forField(password).asRequired()
                .bind(User::getPassword, User::setPassword);

        passwordAgain = new PasswordField();
        binder.forField(passwordAgain).asRequired()
                .withValidator(value -> value.equals(password.getValue()), Localization.get("messages.administration.passwords_must_match"))
                .bind(User::getPassword, User::setPassword);
        formLayout.addComponents(username, password, passwordAgain);
        formLayout.setSizeFull();

        VerticalLayout verticalLayout = new VerticalLayout(formLayout, buttonControls);

        return verticalLayout;
    }

    @Override
    protected void loadItem(User item) {
        binder.readBean(item);
    }

    @Override
    protected void save() {
        User user = new User();
        if (binder.writeBeanIfValid(user)) {
            userService.createUser(user);
            SessionScopedEventBus.post(new BusEvent.RefreshUserGridEvent());
        }
    }

    @Override
    public void localize() {
        super.localize();
        username.setCaption(Localization.get("administration.users.username"));
        password.setCaption(Localization.get("administration.users.password"));
        passwordAgain.setCaption(Localization.get("administration.users.passwordAgain"));
    }
}
