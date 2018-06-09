package com.iwaneez.stuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableScheduling
public class SpringBootApp {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpringBootApp.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);

        LOGGER.info("+----------------------------------+");
        LOGGER.info("| App                              |");
        LOGGER.info("+----------------------------------+");
        LOGGER.info("| http://localhost:8080/           |");
        LOGGER.info("+----------------------------------+");
    }
}
