package com.iwaneez.stuffer.service.impl;

import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.persistence.entity.User;
import com.iwaneez.stuffer.persistence.repository.UserRepository;
import com.iwaneez.stuffer.service.SecurityService;
import com.iwaneez.stuffer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private SecurityService securityService;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(SecurityService securityService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getCurrentUser() {
        String currentUserUsername = securityService.getCurrentUserUsername();
        return userRepository.findOneByUsername(currentUserUsername).get();
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
