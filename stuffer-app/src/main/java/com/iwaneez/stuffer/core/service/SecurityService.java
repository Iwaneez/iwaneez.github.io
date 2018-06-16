package com.iwaneez.stuffer.core.service;

import com.iwaneez.stuffer.core.persistence.entity.RoleType;
import com.iwaneez.stuffer.core.persistence.entity.User;

public interface SecurityService {

    boolean isLoggedIn();

    void login(String username, String password);

    User getCurrentUser();

    boolean hasRole(RoleType type);

}
