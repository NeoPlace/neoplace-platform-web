package com.neoplace.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NeoPlaceApplication {

	public static void main(String[] args) throws JsonProcessingException {
		int i = 7;
		System.out.println(~~~i);
		SpringApplication.run(NeoPlaceApplication.class, args);

	}

}
