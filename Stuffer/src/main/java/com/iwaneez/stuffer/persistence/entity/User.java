package com.iwaneez.stuffer.persistence.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    private Long id;
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private ExchangeProfile activeProfile;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "username", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)})
    public Set<Role> getRoles() {
        return roles;
    }

    @OneToOne
    @JoinColumn(name = "active_profile")
    public ExchangeProfile getActiveProfile() {
        return activeProfile;
    }

    public void setActiveProfile(ExchangeProfile activeProfile) {
        this.activeProfile = activeProfile;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
