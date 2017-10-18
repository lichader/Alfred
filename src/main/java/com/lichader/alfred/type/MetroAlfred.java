package com.lichader.alfred.type;

import com.lichader.alfred.metroapi.v3.DisruptionService;
import com.lichader.alfred.metroapi.v3.RouteService;
import com.lichader.alfred.metroapi.v3.RouteTypeService;
import com.lichader.alfred.metroapi.v3.model.Disruption;
import com.lichader.alfred.metroapi.v3.model.Route;
import com.lichader.alfred.metroapi.v3.model.RouteResponse;
import com.lichader.alfred.slack.MessageBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    private RouteTypeService routeTypeService;

    @Autowired
    private DisruptionService disruptionService;

    @Autowired
    private MessageBot messageBot;


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
                                                        allDisruptions.disruptions.MetroTrain.forEach(this::composeAndSendDisrutpionMessage)
                                                )
                        )
        );

    }

    private boolean isHurtsbridgeLine(Route route){
        return HURTSBRIDGE_LINE.equalsIgnoreCase(route.RouteName);
    }

    private void composeAndSendDisrutpionMessage(Disruption disruption){
        StringBuilder sb = new StringBuilder();
        sb.append("Disruption: ").append(disruption.Description).append(LINE_BREAK);
        sb.append("Status: ").append(disruption.DisruptionStatus).append(LINE_BREAK);
        sb.append("Type: ").append(disruption.DisruptionType).append(LINE_BREAK);
        sb.append("From " + disruption.FromDate + " to " + disruption.ToDate);

        messageBot.send(sb.toString());
    }
}
