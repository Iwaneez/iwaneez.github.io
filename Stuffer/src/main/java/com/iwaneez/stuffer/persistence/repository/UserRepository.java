package com.iwaneez.stuffer.persistence.repository;

import com.iwaneez.stuffer.persistence.entity.ExchangeProfile;
import com.iwaneez.stuffer.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByUsername(String username);

    Optional<User> findOneWithRolesByUsername(String username);

    Optional<User> findOneByActiveProfile(ExchangeProfile exchangeProfile);

}
