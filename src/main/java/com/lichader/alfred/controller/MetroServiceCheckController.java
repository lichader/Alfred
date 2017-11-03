package com.lichader.alfred.controller;

import com.lichader.alfred.logic.HurtsbridgeDisruptionRetrievalLogic;
import com.lichader.alfred.metroapi.v3.DisruptionService;
import com.lichader.alfred.metroapi.v3.RouteTypeService;
import com.lichader.alfred.metroapi.v3.model.Disruption;
import com.lichader.alfred.metroapi.v3.model.RouteTypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/metro")
public class MetroServiceCheckController {

    @Autowired
    private HurtsbridgeDisruptionRetrievalLogic logic;

    @RequestMapping("disruption/hurtsbridge")
    @GetMapping
    public List<Disruption> get(){
        return logic.findDisruptions();
    }

}
