package com.iwaneez.stuffer.ui.view.administration;

import com.iwaneez.stuffer.persistence.entity.User;
import com.iwaneez.stuffer.persistence.repository.UserRepository;
import com.iwaneez.stuffer.ui.component.masterdetail.MasterComponent;
import com.iwaneez.stuffer.util.ApplicationContextUtils;
import com.iwaneez.stuffer.util.Localization;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Grid;

import java.util.stream.Collectors;

public class UserMaster extends MasterComponent<User> {

    private UserRepository userRepository;

    private Grid.Column usernameColumn, roleColumn;


    public UserMaster() {
        userRepository = ApplicationContextUtils.getApplicationContext().getBean(UserRepository.class);
    }

    @Override
    public Grid<User> createGrid() {
        Grid<User> grid = new Grid<>(DataProvider.fromCallbacks(
                query -> userRepository.findAll().stream(),
                query -> ((int) userRepository.count())));
        usernameColumn = grid.addColumn(User::getUsername);
        roleColumn = grid.addColumn(user -> user.getRoles().stream()
                .map(role -> role.getType().name())
                .collect(Collectors.joining(",")));
        grid.setSizeFull();

        return grid;
    }

    @Override
    public void localize() {
        usernameColumn.setCaption(Localization.get("administration.users.username"));
        roleColumn.setCaption(Localization.get("administration.users.role"));
    }

}
