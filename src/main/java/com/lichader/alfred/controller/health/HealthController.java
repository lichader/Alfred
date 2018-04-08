package com.lichader.alfred.controller.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class HealthController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> checkStatus(){
        return new ResponseEntity<String>("I am running", HttpStatus.OK);
    }
}
