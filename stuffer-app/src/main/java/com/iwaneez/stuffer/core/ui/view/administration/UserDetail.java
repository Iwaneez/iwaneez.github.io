package com.iwaneez.stuffer.core.ui.view.administration;

import com.iwaneez.stuffer.core.persistence.entity.Role;
import com.iwaneez.stuffer.core.persistence.entity.User;
import com.iwaneez.stuffer.core.persistence.repository.RoleRepository;
import com.iwaneez.stuffer.core.service.UserService;
import com.iwaneez.stuffer.core.ui.component.masterdetail.DetailComponent;
import com.iwaneez.stuffer.core.util.ApplicationContextUtils;
import com.iwaneez.stuffer.core.util.Localization;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.*;

public class UserDetail extends DetailComponent<User> {

    private UserService userService;
    private RoleRepository roleRepository;

    private TextField username;
    private PasswordField password;
    private PasswordField passwordAgain;
    private TwinColSelect<Role> roleSelect;


    public UserDetail() {
        super(User.class);
        userService = ApplicationContextUtils.getApplicationContext().getBean(UserService.class);
        roleRepository = ApplicationContextUtils.getApplicationContext().getBean(RoleRepository.class);
    }

    @Override
    public Component createDetail(Binder<User> binder) {
        FormLayout formLayout = new FormLayout();

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

        roleSelect = new TwinColSelect<>();
        roleSelect.setDataProvider(DataProvider.fromCallbacks(
                query -> roleRepository.findAll().stream(),
                query -> ((int) roleRepository.count())
        ));
        roleSelect.setItemCaptionGenerator(role -> role.getType().name());
        binder.forField(roleSelect)
                .bind(User::getRoles, User::setRoles);

        formLayout.addComponents(username, password, passwordAgain, roleSelect);

        return formLayout;
    }

    @Override
    protected void loadItem(User item) {
        super.loadItem(item);
        password.setVisible(item.getId() == null);
        passwordAgain.setVisible(item.getId() == null);
    }

    @Override
    protected void getFocus() {
        username.focus();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            return userService.createUser(user);
        }
        return userService.save(user);
    }

    @Override
    public void localize() {
        super.localize();
        username.setCaption(Localization.get("administration.users.username"));
        password.setCaption(Localization.get("administration.users.password"));
        passwordAgain.setCaption(Localization.get("administration.users.passwordAgain"));
        roleSelect.setCaption(Localization.get("administration.users.role"));
    }
}
