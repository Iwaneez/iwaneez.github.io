package com.iwaneez.stuffer.persistence.entity;

import com.iwaneez.stuffer.exchange.bo.SupportedExchange;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "exchange_profile")
public class ExchangeProfile {

    private Long id;
    private String name;
    private String apiKey;
    private String secretKey;
    private SupportedExchange exchange;
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "api_key", nullable = false)
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Column(name = "secret_key", nullable = false)
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "exchange", nullable = false)
    public SupportedExchange getExchange() {
        return exchange;
    }

    public void setExchange(SupportedExchange exchange) {
        this.exchange = exchange;
    }

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeProfile that = (ExchangeProfile) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(apiKey, that.apiKey) &&
                Objects.equals(secretKey, that.secretKey) &&
                exchange == that.exchange &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, apiKey, secretKey, exchange, owner);
    }
}
