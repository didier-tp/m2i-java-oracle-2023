package com.inetum.appliSpringWeb.controller;

import org.springframework.security.core.context.SecurityContextHolder;

/*
 * package "rest" :       @RestController pour WS REST et JSON
 * package "controller" : @Controller pour vieux Spring-MVC avec .jsp
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site")
public class MainController {

	
	//dans index.html <a href="site/login"> .... </a>
	@RequestMapping("/login")
	public String versLogin(Model model) {
		return "login"; // .../jsp/login.jsp
	}
	
	@RequestMapping("/logout")
	public String doLogout(Model model) {
		SecurityContextHolder.getContext().setAuthentication(null);
		return "logout"; // .../jsp/logout.jsp
	}
	
	
}
