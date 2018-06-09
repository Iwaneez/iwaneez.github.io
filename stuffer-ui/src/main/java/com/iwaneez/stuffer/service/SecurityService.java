package com.iwaneez.stuffer.service;

import com.iwaneez.stuffer.persistence.entity.RoleType;

public interface SecurityService {

    boolean isLoggedIn();

    void login(String username, String password);

    String getCurrentUserUsername();

    boolean hasRole(RoleType type);

}
