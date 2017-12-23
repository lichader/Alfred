package com.lichader.alfred.controller;

import com.lichader.alfred.logic.TrainlineDisruptionRetrievalLogic;
import com.lichader.alfred.metroapi.v3.model.Disruption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/metro")
public class MetroServiceCheckController {

    @Autowired
    private TrainlineDisruptionRetrievalLogic logic;

    @RequestMapping("disruptions")
    @GetMapping
    public List<Disruption> get(){
        return logic.findDisruptions();
    }

}
