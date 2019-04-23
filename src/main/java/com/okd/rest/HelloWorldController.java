package com.okd.rest;

import static com.okd.Constant.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okd.dao.service.PhraseDaoService;
import com.okd.entity.User;

@RestController
public class HelloWorldController {
	
	@Autowired
	private PhraseDaoService phraseDaoService;
	
	@Autowired
	private HttpServletRequest request;
	
	Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
	

	@GetMapping("/")
	public String sayHello() {
		return "Hello Java Code Geeks 1!";
	}
	
	
	@GetMapping("/okd/v1/clean")
	public void cleanWindow() {
		Set<String> phrases = phraseDaoService.getAllByPeriod(new Date(Calendar.getInstance().getTime().getTime()), 1);
		String newPhrase = null;
		int i = 1;
		do {
			newPhrase = UUID.randomUUID().toString();
			logger.debug("Attempt number "+i++);
		} while(phrases.contains(newPhrase));
		
		logger.debug("Tu cookie hai?");
		for(Cookie c : request.getCookies()) {
			logger.debug(c.getName() + " - " + c.getValue() );
		}
		
		// Create a phrase
		phraseDaoService.create((User)request.getSession().getAttribute(USER_SESSION_KEY), newPhrase);
		
	}
		
}
