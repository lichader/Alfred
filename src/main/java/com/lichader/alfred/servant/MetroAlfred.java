package com.lichader.alfred.servant;

import com.lichader.alfred.logic.HurtsbridgeDisruptionRetrievalLogic;
import com.lichader.alfred.metroapi.v3.DisruptionService;
import com.lichader.alfred.metroapi.v3.RouteService;
import com.lichader.alfred.metroapi.v3.model.Disruption;
import com.lichader.alfred.metroapi.v3.model.Route;
import com.lichader.alfred.metroapi.v3.model.RouteResponse;
import com.lichader.alfred.slack.MessageBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class MetroAlfred {
    private final Logger logger = LoggerFactory.getLogger(MetroAlfred.class);

    private static final String SCHEDULE_12AM_DAILY = "0 0 0 * * *";

    private static final String LINE_BREAK = "%0A"; // url encoded \n


    @Autowired
    private HurtsbridgeDisruptionRetrievalLogic logic;

    @Autowired
    private MessageBot messageBot;



    @Scheduled(cron = SCHEDULE_12AM_DAILY)
    public void checkDisruption(){
        logger.info("Start checking disruption");

        logic.findDisruptions().forEach(this::composeAndSendDisrutpionMessage);
    }



    // TODO: Move this out to another bean e.g. MessageContentBuilder
    private void composeAndSendDisrutpionMessage(Disruption disruption){
        StringBuilder sb = new StringBuilder();
        sb.append("Disruption: ").append(disruption.Description).append(LINE_BREAK);
        sb.append("Status: ").append(disruption.Status).append(LINE_BREAK);
        sb.append("Type: ").append(disruption.Type).append(LINE_BREAK);
        sb.append("From " + disruption.FromDate + " to " + disruption.ToDate);

        messageBot.send(sb.toString());
    }



}
