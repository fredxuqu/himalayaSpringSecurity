package com.himalaya.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
@MapperScan("com.himalaya.auth.repository")
public class HimalayaAuthServiceApplication {

	private final static Logger LOGGER = LoggerFactory.getLogger(HimalayaAuthServiceApplication.class);

	public static void main(String[] args) {
		LOGGER.info("HimalayaAuthServiceApplication is running...");
		SpringApplication.run(HimalayaAuthServiceApplication.class, args);
	}
}