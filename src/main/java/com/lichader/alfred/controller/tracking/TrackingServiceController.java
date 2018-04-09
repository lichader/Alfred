package com.lichader.alfred.controller.tracking;

import com.lichader.alfred.controller.tracking.model.CreateNewTrackingsWebRequest;
import com.lichader.alfred.controller.tracking.model.TrackingParcelWebModel;
import com.lichader.alfred.db.dao.TrackingParcelRepository;
import com.lichader.alfred.db.model.tracking.TrackingParcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/trackings")
public class TrackingServiceController {

    @Autowired
    private TrackingParcelRepository trackingParcelRepository;

    @RequestMapping(value="{trackingNo}", method = RequestMethod.GET)
    public ResponseEntity<TrackingParcelWebModel> getParcel(@PathVariable("trackingNo") String trackingNo){
        List<TrackingParcel> result = trackingParcelRepository.findByTrackingNoIgnoreCase(trackingNo);

        if (result.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TrackingParcelWebModel response = TrackingParcelWebModel.build(result.get(0));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createNewTrackingParcel(@RequestBody CreateNewTrackingsWebRequest request){
        TrackingParcel newParcel = new TrackingParcel();
        newParcel.setTrackingNo(request.getTrackingNo());
        newParcel.setDestination(request.getDestination());

        trackingParcelRepository.save(newParcel);
    }
}
