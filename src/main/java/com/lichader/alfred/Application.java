package com.lichader.alfred;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by lichader on 9/5/17.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main (String[] args){
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application is starting");
    }
}
