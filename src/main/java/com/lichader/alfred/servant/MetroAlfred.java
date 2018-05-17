package com.lichader.alfred.servant;

import com.lichader.alfred.logic.TrainlineDisruptionRetrievalLogic;
import com.lichader.alfred.metroapi.v3.model.Disruption;
import com.lichader.alfred.slack.MessageBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MetroAlfred {
    private final Logger logger = LoggerFactory.getLogger(MetroAlfred.class);

    public static final String LINE_BREAK = "%0A"; // url encoded \n


    @Autowired
    private TrainlineDisruptionRetrievalLogic logic;

    @Autowired
    private MessageBot messageBot;



    @Scheduled(cron = CronSchedules.TWELVE_AM_DAILY)
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
        sb.append("From ").append(disruption.FromDate != null ? disruption.FromDate.toLocalDateTime() : "N/A")
            .append(" to ").append(disruption.ToDate != null ? disruption.ToDate.toLocalDateTime() : "N/A");

        messageBot.send(sb.toString());
    }



}
