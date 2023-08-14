package com.scrapper.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ScrapperApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ScrapperApplication.class, args);

		// Pobranie beana ScrapperService
		ScrapperService scrapperService = context.getBean(ScrapperService.class);

		// Wywołanie metody do przeszukiwania i zapisywania przepisów
		scrapperService.Scrapper();

		// Zamknięcie kontekstu
		context.close();
	}
}
