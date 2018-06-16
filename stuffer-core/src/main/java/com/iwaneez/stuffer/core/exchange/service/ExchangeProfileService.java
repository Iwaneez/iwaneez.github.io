package com.iwaneez.stuffer.core.exchange.service;

import com.iwaneez.stuffer.core.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.core.persistence.entity.User;

import java.util.List;

public interface ExchangeProfileService {

    void setUserActiveProfile(User user, ExchangeProfile exchangeProfile);

    List<ExchangeProfile> getUserExchangeProfiles(String username);

    int exchangeProfilesCount(String username);

    void deleteExchangeProfile(ExchangeProfile exchangeProfile);

}
