package com.iwaneez.stuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@EnableJpaRepositories("com.iwaneez.stuffer.persistence") // in case jpa repo not visible
public class SpringBootApp {

    private final static Logger log = LoggerFactory.getLogger(SpringBootApp.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);

        log.info("+----------------------------------+");
        log.info("| App                              |");
        log.info("+----------------------------------+");
        log.info("| http://localhost:8080/           |");
        log.info("+----------------------------------+");
    }
}
