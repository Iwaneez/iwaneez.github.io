package com.iwaneez.stuffer.exchange.service.impl;

import com.iwaneez.stuffer.exchange.service.ExchangeProfileService;
import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.persistence.entity.User;
import com.iwaneez.stuffer.persistence.repository.ExchangeProfileRepository;
import com.iwaneez.stuffer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangeProfileServiceImpl implements ExchangeProfileService {

    private UserService userService;
    private ExchangeProfileRepository exchangeProfileRepository;

    @Autowired
    public ExchangeProfileServiceImpl(UserService userService, ExchangeProfileRepository exchangeProfileRepository) {
        this.userService = userService;
        this.exchangeProfileRepository = exchangeProfileRepository;
    }

    @Override
    public void setCurrentUserActiveProfile(ExchangeProfile exchangeProfile) {
        User currentUser = userService.getCurrentUser();
        currentUser.setActiveProfile(exchangeProfile);
        userService.save(currentUser);
    }

    @Override
    public List<ExchangeProfile> getCurrentUserExchangeProfiles() {
        return exchangeProfileRepository.findAllByOwner(userService.getCurrentUser());
    }

    @Override
    public int currentUserExchangeProfileCount() {
        return ((int) exchangeProfileRepository.countByOwner(userService.getCurrentUser()));
    }

    @Override
    public void deleteExchangeProfile(ExchangeProfile exchangeProfile) {
        Optional<User> uOpt = userService.findOneByActiveProfile(exchangeProfile);
        uOpt.ifPresent(user -> {
            user.setActiveProfile(null);
            userService.save(user);
        });
        exchangeProfileRepository.delete(exchangeProfile);
    }
}
