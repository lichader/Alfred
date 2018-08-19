package com.lichader.alfred.servant;

import com.lichader.alfred.bse.BSETrackingPageDownloader;
import com.lichader.alfred.bse.BSETrackingPageParser;
import com.lichader.alfred.db.dao.TrackingParcelRepository;
import com.lichader.alfred.db.dao.TrackingStatusRepository;
import com.lichader.alfred.db.model.tracking.TrackingParcel;
import com.lichader.alfred.db.model.tracking.TrackingStatus;
import com.lichader.alfred.db.model.tracking.TrackingStatusCode;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrackingAlfred {
    private final Logger logger = LoggerFactory.getLogger(TrackingAlfred.class);

    @Autowired
    private TrackingParcelRepository trackingParcelRepository;

    @Autowired
    private TrackingStatusRepository trackingStatusRepository;

    @Autowired
    private BSETrackingPageParser bseTrackingPageParser;

    @Autowired
    private BSETrackingPageDownloader bseTrackingPageDownloader;

    private TrackingStatus DELIVERED_STATUS;

    @PostConstruct
    private void initializeData(){
        DELIVERED_STATUS = trackingStatusRepository.findOneByCode(TrackingStatusCode.DELIVERED.getCode());
    }

    @Scheduled(cron = CronSchedules.TWELVE_AM_DAILY)
    public void checkInProgressAndNotifyMaster() {
        List<TrackingParcel> inProgressParcels = trackingParcelRepository.findByStatusNot(DELIVERED_STATUS);

        Map<String, List<BSETrackingPageParser.BSETrack>> allTrackings =
            inProgressParcels.stream().collect(Collectors.toMap(
                o -> o.getTrackingNo(),
                o -> {
                    Document document = bseTrackingPageDownloader.getTrackingPage(o.getTrackingNo());
                    List<BSETrackingPageParser.BSETrack> trackingRecords = new ArrayList<>();
                    try {
                        trackingRecords = bseTrackingPageParser.extractTrackingInfo(document);
                    } catch (IOException ex) {
                        logger.error("Can't download tracking history for parcel: " + o.getTrackingNo(), ex);
                    }
                    return trackingRecords;
                })
            );
    }
}
