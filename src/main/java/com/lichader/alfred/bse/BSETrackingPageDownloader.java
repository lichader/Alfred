package com.lichader.alfred.bse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BSETrackingPageDownloader {
    private final Logger logger = LoggerFactory.getLogger(BSETrackingPageDownloader.class);

    private static final String HOST = "http://www.blueskyexpress.com.au";

    private static final String FIXED_STATUS_URL_PART = HOST + "/cgi-bin/GInfo.dll?EmsTrackState&cp=65001&cno=";
    private static final String FIXED_TRACKING_URL_PART = HOST + "/cgi-bin/GInfo.dll?EmmisTrack&w=blueskyexpress&cno=";


    public Document getTrackingPage(String trackingNo)  {
        final String url = FIXED_TRACKING_URL_PART + trackingNo;

        try {
            Document doc = Jsoup.connect(url).get();
            return doc;
        } catch (IOException e) {
            logger.error("Unable to download web page: " + url, e);
            throw new RuntimeException(e);
        }
    }

}
