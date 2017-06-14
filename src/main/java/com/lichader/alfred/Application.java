package com.lichader.alfred;

import com.lichader.alfred.service.MetroService;
import com.lichader.alfred.service.model.v3.RouteType;
import com.lichader.alfred.service.model.v3.RouteTypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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


    }
}
