package com.iwaneez.stuffer.persistence.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
// @NamedQuery(name = "Person.findByName", query = "SELECT p FROM Person p WHERE LOWER(p.lastName) = LOWER(?1)")
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persons_id_seq")
    @SequenceGenerator(name = "persons_id_seq", sequenceName = "persons_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "creation_time", nullable = false)
    private Date creationTime;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "modification_time", nullable = false)
    private Date modificationTime;

    @Version
    private long version = 0;

    public Person() {

    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

        Date now = new Date();
        creationTime = now;
        modificationTime = now;
    }

    public Long getId() {
        return id;
    }

    /**
     * This setter method should only be used by unit tests.
     *
     * @param id
     */
    protected void setId(Long id) {
        this.id = id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the full name of the person.
     *
     * @return The full name of the person.
     */
    @Transient
    public String getName() {
        StringBuilder name = new StringBuilder();

        name.append(firstName);
        name.append(" ");
        name.append(lastName);

        return name.toString();
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @PreUpdate
    public void preUpdate() {
        modificationTime = new Date();
    }

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        creationTime = now;
        modificationTime = now;
    }

}
