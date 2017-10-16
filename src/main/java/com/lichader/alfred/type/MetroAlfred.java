package com.lichader.alfred.type;

import com.lichader.alfred.metroapi.v3.DisruptionService;
import com.lichader.alfred.metroapi.v3.RouteService;
import com.lichader.alfred.metroapi.v3.RouteTypeService;
import com.lichader.alfred.metroapi.v3.model.Disruption;
import com.lichader.alfred.metroapi.v3.model.DisruptionsResponse;
import com.lichader.alfred.metroapi.v3.model.Route;
import com.lichader.alfred.metroapi.v3.model.RouteResponse;
import com.lichader.alfred.slack.MessageBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MetroAlfred {
    private final Logger logger = LoggerFactory.getLogger(MetroAlfred.class);

    private static final String LINE_BREAK = "%0A"; // url encoded \n

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteTypeService routeTypeService;

    @Autowired
    private DisruptionService disruptionService;

    @Autowired
    private MessageBot messageBot;


    // Runs in the mid night of every day
    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(fixedRate = 5000)
    public void checkDisruption(){
        logger.info("Start checking disruption");


        RouteResponse allRoutes = routeService.getAll();
        Route hurstbridgeRoute = allRoutes.Routes
                .stream()
                .filter(o -> o.RouteName.equalsIgnoreCase("Hurstbridge")).findFirst().get();
        DisruptionsResponse allDisruptions = disruptionService.getDisruption(hurstbridgeRoute.RouteId);

        List<Disruption> disruptions = allDisruptions.disruptions.MetroTrain;

        for (Disruption dis : disruptions){
            StringBuilder sb = new StringBuilder();
            sb.append("Disruption: ").append(dis.Description).append(LINE_BREAK);
            sb.append("Status: ").append(dis.DisruptionStatus).append(LINE_BREAK);
            sb.append("Type: ").append(dis.DisruptionType).append(LINE_BREAK);
            sb.append("From " + dis.FromDate + " to " + dis.ToDate);

            messageBot.send(sb.toString());
        }
    }
}
