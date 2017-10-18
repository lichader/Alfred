package com.lichader.alfred.util;

import com.lichader.alfred.metroapi.v3.RouteTypeService;
import com.lichader.alfred.metroapi.v3.model.RouteTypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MetroServiceStatusCheckController {

    @Autowired
    private RouteTypeService routeTypeService;

    @RequestMapping("/metro")
    @GetMapping
    public String getHealth(){
        Optional<RouteTypesResponse> optionalResponse = routeTypeService.getAll();

        if (optionalResponse.isPresent()){
            return "Connection to PTV API is ok";
        }

        return "Connection to PTV API is down";
    }
}
