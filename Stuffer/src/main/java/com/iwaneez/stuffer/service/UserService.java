package com.iwaneez.stuffer.service;

import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.persistence.entity.User;

import java.util.Optional;

public interface UserService {

    User getCurrentUser();

    User createUser(User user);

    User save(User user);

    User changeLanguage(User user, String languageTag);

    Optional<User> findOneByActiveProfile(ExchangeProfile exchangeProfile);

}
