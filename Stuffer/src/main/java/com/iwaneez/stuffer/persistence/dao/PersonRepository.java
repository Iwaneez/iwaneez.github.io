package com.iwaneez.stuffer.persistence.dao;

import com.iwaneez.stuffer.persistence.bo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE LOWER(p.lastName) = LOWER(?1)")
    public List<Person> findByLastName(String lastName);

    /**
     * This function calls a named query in META-INF/orm.xml with name
     * Person.findByName
     *
     * @param firstName
     * @param lastName
     * @return A list of persons whose last name and first name are an exact
     * match with the given parameters. If no persons is found, this
     * method returns null.
     */
    public List<Person> findByName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    /**
     * Finds person by using the last name as a search criteria.
     *
     * @param lastName
     * @return A list of persons whose last name is an exact match with the
     * given last name. If no persons is found, this method returns
     * null.
     */
    public List<Person> findByName(String lastName);

    /**
     * Finds persons by using the last name as a search criteria.
     *
     * @param lastName
     * @return A list of persons which last name is an exact match with the
     *         given last name. If no persons is found, this method returns an
     *         empty list.
     */
    // public List<Person> findByLastName(String lastName);

}
