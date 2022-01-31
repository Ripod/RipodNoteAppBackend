package ru.ripod.runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru/ripod/restprocessing","ru.ripod.db","ru.ripod.actionprocessing"})
public class RestServiceStart {
    public static void main(String[] args) {
        SpringApplication.run(RestServiceStart.class, args);
    }
}
