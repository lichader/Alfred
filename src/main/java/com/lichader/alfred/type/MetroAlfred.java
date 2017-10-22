package com.lichader.alfred.type;

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
    private static final String HURTSBRIDGE_LINE = "Hurstbridge";
    private static final String LINE_BREAK = "%0A"; // url encoded \n

    @Autowired
    private RouteService routeService;

    @Autowired
    private DisruptionService disruptionService;

    @Autowired
    private MessageBot messageBot;


    @Value("${advanceDaysToCheck}")
    private int advancedDaysToCheck;


    @Scheduled(cron = SCHEDULE_12AM_DAILY)
//    @Scheduled(fixedRate = 5000)
    public void checkDisruption(){
        logger.info("Start checking disruption");

        Optional<RouteResponse> allRoutes = routeService.getAll();

        allRoutes.ifPresent(
                o -> o.Routes.stream().filter(this::isHurtsbridgeLine).findFirst()
                        .ifPresent(
                                hurtbridgeRoute ->
                                        disruptionService.getDisruption(hurtbridgeRoute.RouteId)
                                                .ifPresent( allDisruptions ->
                                                        allDisruptions.disruptions.MetroTrain.stream().filter(this::isDisruptionHappeningInDays).forEach(this::composeAndSendDisrutpionMessage)
                                                )
                        )
        );

    }

    private boolean isHurtsbridgeLine(Route route){
        return HURTSBRIDGE_LINE.equalsIgnoreCase(route.RouteName);
    }

    // TODO: Move this out to another bean e.g. MessageContentBuilder
    private void composeAndSendDisrutpionMessage(Disruption disruption){
        StringBuilder sb = new StringBuilder();
        sb.append("Disruption: ").append(disruption.Description).append(LINE_BREAK);
        sb.append("Status: ").append(disruption.DisruptionStatus).append(LINE_BREAK);
        sb.append("Type: ").append(disruption.DisruptionType).append(LINE_BREAK);
        sb.append("From " + disruption.FromDate + " to " + disruption.ToDate);

        messageBot.send(sb.toString());
    }

    private boolean isDisruptionHappeningInDays(Disruption disruption){
        LocalDate now = LocalDate.now();
        LocalDate fewDaysLater = now.plusDays(advancedDaysToCheck);

        if (disruption.FromDate.isAfter(fewDaysLater)){
            return false;
        } else if (disruption.FromDate.isBefore(now)){
            return false;
        }

        return true;
    }

}
