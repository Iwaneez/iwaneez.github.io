package com.iwaneez.stuffer.core.service.impl;

import com.iwaneez.stuffer.core.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.core.persistence.entity.User;
import com.iwaneez.stuffer.core.persistence.repository.UserRepository;
import com.iwaneez.stuffer.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User changeLanguage(User user, String languageTag) {
        user.setLanguage(languageTag);

        return save(user);
    }

    @Override
    public Optional<User> findOneByActiveProfile(ExchangeProfile exchangeProfile) {
        return userRepository.findOneByActiveProfile(exchangeProfile);
    }
}
