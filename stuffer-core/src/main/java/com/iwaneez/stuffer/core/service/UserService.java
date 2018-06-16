package com.iwaneez.stuffer.core.service;

import com.iwaneez.stuffer.core.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.core.persistence.entity.User;

import java.util.Optional;

public interface UserService {

    User createUser(User user);

    User save(User user);

    User changeLanguage(User user, String languageTag);

    Optional<User> findOneByActiveProfile(ExchangeProfile exchangeProfile);

}
