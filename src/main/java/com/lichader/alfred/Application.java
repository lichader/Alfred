package com.lichader.alfred;

import com.lichader.alfred.metroapi.v3.DisruptionService;
import com.lichader.alfred.metroapi.v3.MetroService;
import com.lichader.alfred.metroapi.v3.RouteService;
import com.lichader.alfred.metroapi.v3.RouteTypeService;
import com.lichader.alfred.metroapi.v3.model.*;
import com.lichader.alfred.slack.MessageBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Created by lichader on 9/5/17.
 */
@SpringBootApplication
public class Application implements CommandLineRunner{

    private static final String LINE_BREAK = "%0A"; // url encoded \n

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteTypeService routeTypeService;

    @Autowired
    private DisruptionService disruptionService;

    @Autowired
    private MessageBot messageBot;


    public static void main (String[] args){
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application is starting");

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
