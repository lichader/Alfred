package com.lichader.alfred;

import com.lichader.alfred.metroapi.v3.MetroService;
import com.lichader.alfred.metroapi.v3.model.*;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

/**
 * Created by lichader on 9/5/17.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private MetroService metroService;

    public static void main (String[] args){
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application is starting");

        RouteTypesResponse allTypes = metroService.getRouteTypes();

        Optional<RouteType> possibleTrainType = allTypes.RouteTypes
                                    .stream()
                                    .filter(o -> o.Name.equalsIgnoreCase("Train"))
                                    .findFirst();

        if (!possibleTrainType.isPresent()){
            throw new RuntimeException("Can't find train route type");
        }

        RouteType  trainType = possibleTrainType.get();

        RouteResponse allRoutes = metroService.getRoutes();

        Route hurstbridgeRoute = allRoutes.Routes
                                .stream()
                                .filter(o -> o.RouteName.equalsIgnoreCase("Hurstbridge")).findFirst().get();

        DisruptionsResponse allDisruptions = metroService.getDisruption(hurstbridgeRoute.RouteId);

        if (!allDisruptions.disruptions.MetroTrain.isEmpty()){
            List<Disruption> disruptions = allDisruptions.disruptions.MetroTrain;

            for (Disruption dis : disruptions){
                System.out.println("###########################");
                System.out.println("Disruption: ");
                System.out.println(dis.Description);
                System.out.println(dis.DisruptionStatus);
                System.out.println(dis.DisruptionType);
                System.out.println("from " + dis.FromDate + " to " + dis.ToDate);
                System.out.println("###########################");
                System.out.println();
            }
        }
    }
}
