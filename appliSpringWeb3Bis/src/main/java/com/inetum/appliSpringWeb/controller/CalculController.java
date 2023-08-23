package com.inetum.appliSpringWeb.controller;

/*
 * package "rest" :       @RestController pour WS REST et JSON
 * package "controller" : @Controller pour vieux Spring-MVC avec .jsp
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calcul")
public class CalculController {
	
	// private ServiceCompte serviceCompte avec @Autowired possible ici
	
	//dans index.html <a href="calcul/saisieHt"> .... </a>
	@RequestMapping("/saisieHt")
	public String versSasieTva(Model model) {
		return "declencherCalcul"; // .../jsp/declencherCalcul.jsp
	}
	
	
	//URL : http://localhost:8080/appliSpringWeb/calcul/tva?ht=200&tauxTvaPct=20
	//ou bien
	//URL : http://localhost:8080/appliSpringWeb/calcul/tva avec données saisies
	//dans formulaire html/jsp et "submit"
	@RequestMapping("/tva")
	public String calculerTva(Model model,
			@RequestParam(name="ht")double ht,
			@RequestParam(name="tauxTvaPct")double tauxTvaPct) {
		//calcul des parties du resultat
		double tva = ht * tauxTvaPct/100;
		double ttc = ht + tva;
		//on stocke le resultat dans le model qui sera recupéré par la page JSP "resTva.jsp"
		model.addAttribute("tva", tva);
		model.addAttribute("ttc", ttc);
		
		return "resTva"; //on retourne le nom de la vue qui va devoir mettre 
		                 //en "page html" le resultat (ici "resTva.jsp")
	}

	
}
