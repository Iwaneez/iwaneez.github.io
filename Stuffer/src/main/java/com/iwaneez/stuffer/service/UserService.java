package com.iwaneez.stuffer.service;

import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.persistence.entity.User;

import java.util.Optional;

public interface UserService {

    User getCurrentUser();

    Optional<User> findOneByActiveProfile(ExchangeProfile exchangeProfile);

    User save(User user);

    User createUser(User user);

    User updatePassword(User user);

}
