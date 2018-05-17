package com.lichader.alfred.controller.tracking;

import com.lichader.alfred.controller.tracking.model.CreateNewTrackingsWebRequest;
import com.lichader.alfred.controller.tracking.model.TrackingParcelWebModel;
import com.lichader.alfred.db.dao.TrackingParcelRepository;
import com.lichader.alfred.db.dao.TrackingStatusRepository;
import com.lichader.alfred.db.model.tracking.TrackingParcel;
import com.lichader.alfred.db.model.tracking.TrackingStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/trackings")
public class TrackingServiceController {

    @Autowired
    private TrackingParcelRepository trackingParcelRepository;

    @Autowired
    private TrackingStatusRepository trackingStatusRepository;

    @RequestMapping(value="{trackingNo}", method = RequestMethod.GET)
    public ResponseEntity<TrackingParcelWebModel> getParcel(@PathVariable("trackingNo") String trackingNo){
        TrackingParcel result = trackingParcelRepository.findOneByTrackingNoIgnoreCase(trackingNo);

        if (result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TrackingParcelWebModel response = TrackingParcelWebModel.build(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<TrackingParcelWebModel> createNewTrackingParcel(@RequestBody CreateNewTrackingsWebRequest request){
        if(trackingParcelRepository.findOneByTrackingNoIgnoreCase(request.getTrackingNo()) != null){
            return new ResponseEntity<TrackingParcelWebModel>(HttpStatus.CONFLICT);
        }

        TrackingParcel newParcel = new TrackingParcel();
        newParcel.setTrackingNo(request.getTrackingNo());
        newParcel.setStatus(trackingStatusRepository.findOneByCode(TrackingStatusCode.IN_PROGRESS.getCode()));

        newParcel = trackingParcelRepository.save(newParcel);

        TrackingParcelWebModel response = TrackingParcelWebModel.build(newParcel);
        return new ResponseEntity<TrackingParcelWebModel>(response, HttpStatus.CREATED);
    }
}
