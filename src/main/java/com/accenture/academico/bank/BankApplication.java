package com.accenture.academico.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BankApplication {

	private static Logger logger = LoggerFactory.getLogger(BankApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
		logger.info(":: API BANK inicidada e pronta para receber requisições");
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String hello() {
		return "API de Sistema bancário BANK";
	}

}
