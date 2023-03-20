package com.cygnus.jett;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling
public class JettRulesApplication {

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(JettRulesApplication.class, args);
		log.info("init app jett-rules-api time zone defaul={}", TimeZone.getDefault());
	}
}
