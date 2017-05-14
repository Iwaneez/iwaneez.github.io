package com.iwaneez.stuffer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories("com.iwaneez.stuffer.persistence") // in case jpa repo not visible
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
