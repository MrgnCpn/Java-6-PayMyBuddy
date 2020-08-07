package com.paymybuddy.PayMyBuddyWeb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication
public class PayMyBuddyWebApplication {
	private static final Logger logger = LogManager.getLogger("PayMyBuddyWebApplication");

	public static void main(String[] args) {
		logger.info("Application start");
		SpringApplication.run(PayMyBuddyWebApplication.class, args);
		logger.info("Application running");
	}

}