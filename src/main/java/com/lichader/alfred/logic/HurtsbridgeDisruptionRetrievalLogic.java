package com.lichader.alfred.logic;

import com.lichader.alfred.metroapi.v3.DisruptionService;
import com.lichader.alfred.metroapi.v3.RouteService;
import com.lichader.alfred.metroapi.v3.model.Disruption;
import com.lichader.alfred.metroapi.v3.model.Route;
import com.lichader.alfred.metroapi.v3.model.RouteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class HurtsbridgeDisruptionRetrievalLogic {

    private static final String HURTSBRIDGE_LINE = "Hurstbridge";

    @Autowired
    private RouteService routeService;

    @Autowired
    private DisruptionService disruptionService;

    @Value("${advanceDaysToCheck}")
    private int advancedDaysToCheck;

    public List<Disruption> findDisruptions(){

        List<Disruption> result = new ArrayList<>();

        Optional<RouteResponse> allRoutes = routeService.getAll();

        allRoutes.ifPresent(
            o -> o.Routes.stream().filter(this::isHurtsbridgeLine).findFirst()
                .ifPresent(
                    hurtbridgeRoute ->
                        disruptionService.getDisruption(hurtbridgeRoute.RouteId)
                            .ifPresent( allDisruptions ->
                                allDisruptions.disruptions.MetroTrain.stream().filter(this::isDisruptionHappeningInDays).collect(Collectors.toCollection(() -> result))
                            )
                )
        );

        return result;
    }

    private boolean isHurtsbridgeLine(Route route){
        return HURTSBRIDGE_LINE.equalsIgnoreCase(route.RouteName);
    }


    private boolean isDisruptionHappeningInDays(Disruption disruption){
        LocalDate now = LocalDate.now();
        LocalDate fewDaysLater = now.plusDays(advancedDaysToCheck);

        if (disruption.FromDate.toLocalDate().isAfter(fewDaysLater)){
            return false;
        } else if (disruption.FromDate.toLocalDate().isBefore(now)){
            return false;
        }

        return true;
    }


}
