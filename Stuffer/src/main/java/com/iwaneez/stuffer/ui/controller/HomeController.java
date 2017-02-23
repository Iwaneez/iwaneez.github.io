package com.iwaneez.stuffer.ui.controller;

import com.iwaneez.stuffer.persistence.bo.entity.Person;
import com.iwaneez.stuffer.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Resource
	private PersonService personService;

    @Autowired
    private MessageSource messageSource;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private static final String VIEW_HOME = "home";
    private static final String VIEW_PLACE_LAYOUT = "place/placeLayout";

    private static final String ATTRIBUTEID_MESSAGE = "msg";

    private static final String MESSAGE_PERSON_INSERTED = "com.iwaneez.home.person.inserted";
    private static final String MESSAGE_PERSON_RETRIEVED = "com.iwaneez.home.person.retrieved";
    private static final String MESSAGE_PERSON_REMOVED = "com.iwaneez.home.person.removed";
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createPerson(@RequestParam(value="firstName") String firstName, 
				    @RequestParam(value="lastName") String lastName, 
				    Model model, Locale locale) {
		Person person = personService.create(new Person(firstName, lastName));

		String msg = messageSource.getMessage(MESSAGE_PERSON_INSERTED, new Object[]{person.getId(), person.getName()}, locale);
		model.addAttribute(ATTRIBUTEID_MESSAGE, msg);

		return VIEW_HOME;
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String getPerson(@PathVariable(value="id") Long id, 
				    Model model, Locale locale) {
		Person person = personService.findByID(id);

        String msg = messageSource.getMessage(MESSAGE_PERSON_RETRIEVED, new Object[]{person.getId(), person.getName()}, locale);
        model.addAttribute(ATTRIBUTEID_MESSAGE, msg);

        return VIEW_HOME;
	}
	
	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String removePerson(@PathVariable(value="id") Long id, 
						Model model, Locale locale) {
        Person person = personService.delete(id);

        String msg = messageSource.getMessage(MESSAGE_PERSON_REMOVED, new Object[]{person.getName()}, locale);
        model.addAttribute(ATTRIBUTEID_MESSAGE, msg);

        return VIEW_HOME;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		//Date date = new Date();
		//DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		//String formattedDate = dateFormat.format(date);
		
		model.addAttribute(ATTRIBUTEID_MESSAGE, "Default page with no action possible.");
		
		return VIEW_HOME;
	}
	
	@RequestMapping(value = "/placeLayout.html", method = RequestMethod.GET)
    public String getPlaceLayout() {

	    return VIEW_PLACE_LAYOUT;
    }
	
}
