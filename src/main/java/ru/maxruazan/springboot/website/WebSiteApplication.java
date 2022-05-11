package ru.maxruazan.springboot.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class WebSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSiteApplication.class, args);
	}

}
