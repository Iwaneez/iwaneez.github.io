package com.iwaneez.stuffer.core.exchange.service.impl;

import com.iwaneez.stuffer.core.exchange.service.ExchangeProfileService;
import com.iwaneez.stuffer.core.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.core.persistence.entity.User;
import com.iwaneez.stuffer.core.persistence.repository.ExchangeProfileRepository;
import com.iwaneez.stuffer.core.service.UserService;
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
    public void setUserActiveProfile(User user, ExchangeProfile exchangeProfile) {
        user.setActiveProfile(exchangeProfile);
        userService.save(user);
    }

    @Override
    public List<ExchangeProfile> getUserExchangeProfiles(String username) {
        return exchangeProfileRepository.findAllByOwnerUsername(username);
    }

    @Override
    public int exchangeProfilesCount(String username) {
        return ((int) exchangeProfileRepository.countByOwnerUsername(username));
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
