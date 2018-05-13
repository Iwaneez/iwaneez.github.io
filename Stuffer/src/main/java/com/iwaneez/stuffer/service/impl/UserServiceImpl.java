package com.iwaneez.stuffer.service.impl;

import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.persistence.entity.Role;
import com.iwaneez.stuffer.persistence.entity.RoleType;
import com.iwaneez.stuffer.persistence.entity.User;
import com.iwaneez.stuffer.persistence.repository.RoleRepository;
import com.iwaneez.stuffer.persistence.repository.UserRepository;
import com.iwaneez.stuffer.service.SecurityService;
import com.iwaneez.stuffer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private SecurityService securityService;

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(SecurityService securityService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getCurrentUser() {
        String currentUsername = securityService.getCurrentUsername();
        return userRepository.findOneByUsername(currentUsername).get();
    }

    @Override
    public Optional<User> findOneByActiveProfile(ExchangeProfile exchangeProfile) {
        return userRepository.findOneByActiveProfile(exchangeProfile);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User createUser(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        Optional<Role> roleOptional = roleRepository.findByType(RoleType.USER);
        roleOptional.ifPresent(role -> {
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        });

        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

}
