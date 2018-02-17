package com.iwaneez.stuffer.service;

public interface SecurityService {

    boolean isLoggedIn();

    void login(String username, String password);

    String getCurrentUsername();

    boolean hasRole(String role);

}
