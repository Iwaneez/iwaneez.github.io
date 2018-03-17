package com.iwaneez.stuffer.service.impl;

import com.iwaneez.stuffer.persistence.entity.Person;
import com.iwaneez.stuffer.persistence.dao.PersonRepository;
import com.iwaneez.stuffer.service.PersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Resource
    private PersonRepository personRepository;

    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person findByID(Long id) {
        return personRepository.findOne(id);
    }

    @Override
    public Person findByLastName(String lastName) {
        return personRepository.findByLastName(lastName).get(0);
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person delete(Long id) {
        Person p = personRepository.findOne(id);
        personRepository.delete(id);
		
        return p;
    }

}
