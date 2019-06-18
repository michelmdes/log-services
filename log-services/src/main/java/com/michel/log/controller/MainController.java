package com.michel.log.controller;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controladora que redireciona a url inicial do sistema para o link da documentacao da API
 * Documentacao: [ApplicationPath]/swagger-ui.html
 * @author Michel Mendes	17/06/2019
 */
@Controller
public class MainController {
	
	final static Logger logger = Logger.getLogger(MainController.class.getName());
	
	@RequestMapping(value="/", method= RequestMethod.GET)	
	public String homeDefault(){	
		logger.info("Redirecionando para a documentacao gerada pelo Swagger");
	    return "redirect:/swagger-ui.html";
	}
	
}

