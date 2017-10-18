package com.lichader.alfred.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @RequestMapping("/status")
    @GetMapping
    public String getHealth(){
        return "I am running";
    }
}
