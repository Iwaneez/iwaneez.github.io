package com.iwaneez.stuffer.ui.controller;

import com.iwaneez.stuffer.persistence.bo.entity.Person;
import com.iwaneez.stuffer.service.PersonService;

import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Resource
	PersonService personService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createPerson(@RequestParam(value="firstName") String firstName, 
				    @RequestParam(value="lastName") String lastName, 
				    Model model) {
		Person person = personService.create(new Person(firstName, lastName));
		model.addAttribute("msg", "Person [" + person.getId() + "]" + person.getName() + " inserted into db.");
		
		return "home";
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String getPerson(@PathVariable(value="id") Long id, 
				    Model model) {
		Person person = personService.findByID(id);
		model.addAttribute("msg", "Retrieved "+person.getName() + ".");
		
		return "home";
	}
	
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String removePerson(@PathVariable(value="id") Long id, 
						Model model) {
		Person person = personService.findByID(id);
		personService.delete(id);
		model.addAttribute("msg", person.getName() + " removed.");
		
		return "home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		//Date date = new Date();
		//DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		//String formattedDate = dateFormat.format(date);
		
		model.addAttribute("msg", "Default page with no action possible.");
		
		return "home";
	}
	
	@RequestMapping(value = "/placeLayout.html", method = RequestMethod.GET)
        public String getPlaceLayout() {
                
                return "place/placeLayout";
        }
	
}
