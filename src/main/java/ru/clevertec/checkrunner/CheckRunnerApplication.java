package ru.clevertec.checkrunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(value = "ru.clevertec.checkrunner.config")
public class CheckRunnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckRunnerApplication.class, args);
    }
}
