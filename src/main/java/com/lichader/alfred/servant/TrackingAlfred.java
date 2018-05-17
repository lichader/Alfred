package com.lichader.alfred.servant;

import com.lichader.alfred.bse.BSETrackingPageParser;
import com.lichader.alfred.db.dao.TrackingParcelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TrackingAlfred {
    private final Logger logger = LoggerFactory.getLogger(TrackingAlfred.class);

    @Autowired
    private TrackingParcelRepository trackingParcelRepository;


    private BSETrackingPageParser bseTrackingPageParser;

    public TrackingAlfred(){
        bseTrackingPageParser = new BSETrackingPageParser();
    }

    @Scheduled(cron = CronSchedules.TWELVE_AM_DAILY)
    public void checkInProgressAndNotifyMaster(){
//        List<TrackingParcel> inProgressParcels = trackingParcelRepository.findByDeliveredFalse();


    }
}
