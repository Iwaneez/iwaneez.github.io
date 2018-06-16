package com.iwaneez.stuffer.core.persistence.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role")
public class Role {

    private Long id;
    private RoleType type;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                type == role.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
