package org.igorM.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "org.igorM.repositories")
@EntityScan(basePackages = "org.igorM.entity")
@SpringBootApplication(scanBasePackages = {"org.igorM"})
public class Starter {
    public static void main(String[] args)  {
        SpringApplication.run(Starter.class, args);
    }

}
