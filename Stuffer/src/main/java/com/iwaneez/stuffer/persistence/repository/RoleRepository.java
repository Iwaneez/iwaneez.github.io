package com.iwaneez.stuffer.persistence.repository;

import com.iwaneez.stuffer.persistence.entity.Role;
import com.iwaneez.stuffer.persistence.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByType(RoleType type);

}
