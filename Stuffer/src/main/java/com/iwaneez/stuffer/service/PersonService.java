package com.iwaneez.stuffer.service;

import com.iwaneez.stuffer.persistence.bo.entity.Person;

import java.util.List;

public interface PersonService {
	
	public Person create(Person person);
	
	public Person findByID(Long id);
	
	public Person findByLastName(String lastName);
	
	public List<Person> findAll();
	
	public Person delete(Long id);
	
}
