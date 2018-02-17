package com.iwaneez.stuffer.service.impl;

import com.iwaneez.stuffer.persistence.entity.Role;
import com.iwaneez.stuffer.persistence.entity.User;
import com.iwaneez.stuffer.persistence.repository.RoleRepository;
import com.iwaneez.stuffer.persistence.repository.UserRepository;
import com.iwaneez.stuffer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        Role role = roleRepository.findOne(Role.USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        user.setPassword(encryptedPassword);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

}
