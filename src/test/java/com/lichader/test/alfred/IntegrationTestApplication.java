package com.lichader.test.alfred;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lichader.alfred.*")
public class IntegrationTestApplication {

    public static void main (String[] args){
        SpringApplication app = new SpringApplication(IntegrationTestApplication.class);
        app.run(args);
    }
}
