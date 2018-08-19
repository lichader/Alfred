package com.lichader.alfred.test.db.dao;

import com.lichader.alfred.db.dao.TrackingStatusRepository;
import com.lichader.alfred.db.model.tracking.TrackingStatus;
import com.lichader.alfred.test.AbstractSpringBootTestBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
public class TrackingStatusRepositoryTest extends AbstractSpringBootTestBase {

    private final int SENT = 1;
    private final String SENT_DESCRIPTION = "Sent";
    private final int IN_PROGRESS = 2;
    private final String IN_PROGRESS_STATUS = "In progress";

    @Autowired
    private TrackingStatusRepository trackingStatusRepository;

    @Before
    public void setupData(){
        TrackingStatus sentStatus = new TrackingStatus(SENT, SENT_DESCRIPTION);
        sentStatus = trackingStatusRepository.save(sentStatus);

        TrackingStatus inProgressStatus = new TrackingStatus(IN_PROGRESS, IN_PROGRESS_STATUS);
        inProgressStatus = trackingStatusRepository.save(inProgressStatus);
    }

    @Test
    public void findStatusByCode_ExpectOneReturn(){
        TrackingStatus result = trackingStatusRepository.findOneByCode(SENT);

        assertNotNull(result);
        assertEquals(SENT, result.getCode());
        assertEquals(SENT_DESCRIPTION, result.getDescription());
    }
}
