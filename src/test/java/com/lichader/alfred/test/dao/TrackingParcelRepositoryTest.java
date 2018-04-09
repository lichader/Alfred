package com.lichader.alfred.test.dao;

import com.lichader.alfred.db.dao.TrackingParcelRepository;
import com.lichader.alfred.db.model.tracking.TrackingHistory;
import com.lichader.alfred.db.model.tracking.TrackingParcel;
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
    private String destination = "CHINA";

    private String location = "Hunan";
    private String status = "In Progress";

    @Autowired
    private TrackingParcelRepository trackingParcelRepository;

    @Before
    public void setupData(){
        TrackingParcel trackingParcel = new TrackingParcel();
        trackingParcel.setTrackingNo(trackingNo);
        trackingParcel.setDestination(destination);
        trackingParcel.setDelivered(false);

        TrackingHistory trackingHistory = new TrackingHistory();
        trackingHistory.setTime(LocalDateTime.now());
        trackingHistory.setLocation(location);
        trackingHistory.setStatus(status);
        trackingParcel.getHistories().add(trackingHistory);

        trackingParcelRepository.save(trackingParcel);

        trackingParcel = new TrackingParcel();
        trackingParcel.setTrackingNo(trackingNo + "2");
        trackingParcel.setDestination(destination);
        trackingParcel.setDelivered(true);
        trackingParcelRepository.save(trackingParcel);
    }

    @Test
    public void findParcelByTrackingNoInLowerCase_ExpectOneReturn(){

        List<TrackingParcel> result = trackingParcelRepository.findByTrackingNoIgnoreCase(trackingNo.toLowerCase());

        assertEquals(1, result.size());

        TrackingParcel parcel = result.get(0);

        assertEquals(trackingNo, parcel.getTrackingNo());
        assertEquals(destination, parcel.getDestination());
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
        List<TrackingParcel> result = trackingParcelRepository.findByDeliveredFalse();

        assertEquals(1, result.size());
        assertEquals(trackingNo , result.get(0).getTrackingNo());
    }

}
