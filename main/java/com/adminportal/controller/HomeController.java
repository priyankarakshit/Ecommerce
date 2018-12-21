package com.adminportal.controller;

import com.adminportal.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class HomeController {
	
	
	private static final Logger logging = Logger.getLogger(AdminportalApplication.class.getName());
	@RequestMapping("/")
	public String index(){
		logging.log(Level.INFO, "you were redirected to home page!");
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public String home(){
		logging.log(Level.INFO, "you're at the home page!");
		return "home";
	}
	
	@RequestMapping("/login")
	public String login(){
		logging.log(Level.INFO, "you're at the login page");
		return "login";
	}
}
