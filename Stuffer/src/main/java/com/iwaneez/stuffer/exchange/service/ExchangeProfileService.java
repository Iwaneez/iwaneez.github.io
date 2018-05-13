package com.iwaneez.stuffer.exchange.service;

import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;

import java.util.List;

public interface ExchangeProfileService {

    void setCurrentUserActiveProfile(ExchangeProfile exchangeProfile);

    List<ExchangeProfile> getCurrentUserExchangeProfiles();

    int currentUserExchangeProfileCount();

    void deleteExchangeProfile(ExchangeProfile exchangeProfile);

}
