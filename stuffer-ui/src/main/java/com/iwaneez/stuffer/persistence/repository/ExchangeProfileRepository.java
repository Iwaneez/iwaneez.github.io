package com.iwaneez.stuffer.persistence.repository;

import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeProfileRepository extends JpaRepository<ExchangeProfile, Long> {

    List<ExchangeProfile> findAllByOwner(User owner);

    long countByOwner(User owner);

}
