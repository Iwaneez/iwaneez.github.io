package com.iwaneez.stuffer.core.persistence.repository;

import com.iwaneez.stuffer.core.persistence.entity.ExchangeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeProfileRepository extends JpaRepository<ExchangeProfile, Long> {

    List<ExchangeProfile> findAllByOwnerUsername(String username);

    long countByOwnerUsername(String username);

}
