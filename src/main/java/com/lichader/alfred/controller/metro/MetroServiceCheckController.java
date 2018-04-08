package com.lichader.alfred.controller.metro;

import com.lichader.alfred.logic.TrainlineDisruptionRetrievalLogic;
import com.lichader.alfred.metroapi.v3.model.Disruption;
import com.lichader.alfred.servant.MetroAlfred;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Disruption>> getAllDisruptions(){
        return new ResponseEntity<>(logic.findDisruptions(), HttpStatus.OK);
    }

    @RequestMapping(value = "disruptions", method = RequestMethod.POST)
    public ResponseEntity<String> sendMessageToSlack(){
        alfred.checkDisruption();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
