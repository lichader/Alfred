package com.lichader.alfred.test.db.dao;

import com.lichader.alfred.db.dao.TrackingParcelRepository;
import com.lichader.alfred.db.dao.TrackingStatusRepository;
import com.lichader.alfred.db.model.tracking.TrackingHistory;
import com.lichader.alfred.db.model.tracking.TrackingParcel;
import com.lichader.alfred.db.model.tracking.TrackingStatus;
import com.lichader.alfred.test.AbstractSpringBootTestBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
public class TrackingParcelRepositoryTest extends AbstractSpringBootTestBase {

    private String trackingNo = "TEST1234";

    private String location = "Hunan";
    private String status = "In Progress";

    private TrackingStatus deliveredStatus;
    private TrackingStatus inFlightStatus;

    @Autowired
    private TrackingParcelRepository trackingParcelRepository;

    @Autowired
    private TrackingStatusRepository trackingStatusRepository;

    @Before
    public void setupData(){
        deliveredStatus = new TrackingStatus(1, "Delivered");
        deliveredStatus = trackingStatusRepository.save(deliveredStatus);

        inFlightStatus= new TrackingStatus(2, "In progress");
        inFlightStatus = trackingStatusRepository.save(inFlightStatus);

        TrackingParcel trackingParcel = new TrackingParcel();
        trackingParcel.setTrackingNo(trackingNo);
        trackingParcel.setStatus(inFlightStatus);

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setTime(LocalDateTime.now());
        trackingHistory.setLocation(location);
        trackingHistory.setStatus(status);
        trackingParcel.getHistories().add(trackingHistory);

        trackingParcelRepository.save(trackingParcel);

        trackingParcel = new TrackingParcel();
        trackingParcel.setTrackingNo(trackingNo + "2");
        trackingParcel.setStatus(deliveredStatus);
        trackingParcelRepository.save(trackingParcel);
    }

    @Test
    public void findParcelByTrackingNoInLowerCase_ExpectOneReturn(){

        TrackingParcel parcel = trackingParcelRepository.findOneByTrackingNoIgnoreCase(trackingNo.toLowerCase());

        assertNotNull(parcel);

        assertEquals(trackingNo, parcel.getTrackingNo());
        assertNotNull(parcel.getCreatedDate());
        assertNotNull(parcel.getLastModifiedDate());

        assertEquals(1, parcel.getHistories().size());
        TrackingHistory history = parcel.getHistories().get(0);
        assertEquals(location, history.getLocation());
        assertEquals(status, history.getStatus());
        assertNotNull(history.getCreatedDate());
        assertNotNull(history.getLastModifiedDate());
    }

    @Test
    public void findInProgress_ExpectOneReturn(){
        List<TrackingParcel> result = trackingParcelRepository.findByStatus(inFlightStatus);

        assertEquals(1, result.size());
        assertEquals(trackingNo , result.get(0).getTrackingNo());
    }

    @Test
    public void findNotInProgress_ExpectOneREturn(){
        List<TrackingParcel> result = trackingParcelRepository.findByStatusNot(inFlightStatus);

        assertEquals(1, result.size());
        assertEquals(trackingNo + "2", result.get(0).getTrackingNo());
    }

}
