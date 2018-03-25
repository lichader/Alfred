package com.lichader.alfred.controller;

import com.lichader.alfred.logic.TrainlineDisruptionRetrievalLogic;
import com.lichader.alfred.metroapi.v3.model.Disruption;
import com.lichader.alfred.servant.MetroAlfred;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/metro")
public class MetroServiceCheckController {

    @Autowired
    private TrainlineDisruptionRetrievalLogic logic;

    @Autowired
    private MetroAlfred alfred;

    @RequestMapping(value = "disruptions", method = RequestMethod.GET)
    public List<Disruption> get(){
        return logic.findDisruptions();
    }

    @RequestMapping(value = "disruptions", method = RequestMethod.POST)
    public void sendMessageToSlack(){
        alfred.checkDisruption();
    }
}
