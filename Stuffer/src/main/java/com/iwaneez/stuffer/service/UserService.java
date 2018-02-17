package com.iwaneez.stuffer.service;

import com.iwaneez.stuffer.persistence.entity.User;

public interface UserService {

    User createUser(User user);

    User updatePassword(User user);

}
