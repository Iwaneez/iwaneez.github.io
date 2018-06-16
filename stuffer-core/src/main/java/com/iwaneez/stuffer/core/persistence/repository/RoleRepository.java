package com.iwaneez.stuffer.core.persistence.repository;

import com.iwaneez.stuffer.core.persistence.entity.Role;
import com.iwaneez.stuffer.core.persistence.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByType(RoleType type);

}
