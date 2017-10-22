package com.lichader.test.alfred.unit.metro;

import com.lichader.alfred.metroapi.v3.DisruptionService;
import com.lichader.alfred.metroapi.v3.RouteService;
import com.lichader.alfred.metroapi.v3.model.*;
import com.lichader.alfred.slack.MessageBot;
import com.lichader.alfred.type.MetroAlfred;
import com.lichader.test.alfred.AbstractSpringBootTestBase;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class MetroAlfredTest extends AbstractSpringBootTestBase{

    @MockBean
    private RouteService routeService;

    @MockBean
    private DisruptionService disruptionService;

    @MockBean
    private MessageBot messageBot;

    @Autowired
    MetroAlfred metroAlfred;

    @Test
    public void checkDisruption_TwoDisruptions_ExpectTwoMessageSent(){
        given(this.routeService.getAll()).willReturn(Optional.of(mockRoutes()));
        given(this.disruptionService.getDisruption(1)).willReturn(Optional.of(mockDisruptions()));

        metroAlfred.checkDisruption();
        then(this.messageBot).should(times(2)).send(Mockito.anyString());
    }

    private RouteResponse mockRoutes(){
        RouteResponse routeResponse = new RouteResponse();

        Route route1 = new Route();
        route1.RouteId = 1;
        route1.RouteName = "Hurstbridge";
        routeResponse.Routes.add(route1);

        Route route2 = new Route();
        route2.RouteId = 2;
        route2.RouteName = "South Morang";
        routeResponse.Routes.add(route2);

        Route route3 = new Route();
        route3.RouteId = 3;
        route3.RouteName = "Werriebie";
        routeResponse.Routes.add(route3);

        return routeResponse;
    }

    private DisruptionsResponse mockDisruptions() {
        DisruptionsResponse disruptionsResponse = new DisruptionsResponse();

        disruptionsResponse.disruptions = new Disruptions();

        Disruption disruption1 = new Disruption();
        disruption1.DisruptionId = 1;
        disruption1.Description = "Test disruption 1";
        disruption1.DisruptionStatus = "planned";
        disruption1.DisruptionType = "test";
        disruption1.PublishedOn = LocalDate.now();
        disruption1.LastUpdated = LocalDate.now();
        disruption1.FromDate = LocalDate.now().plusDays(4);
        disruption1.ToDate = LocalDate.now().plusDays(11);

        disruptionsResponse.disruptions.MetroTrain.add(disruption1);

        Disruption disruption2 = new Disruption();
        disruption2.DisruptionId = 1;
        disruption2.Description = "Test disruption 1";
        disruption2.DisruptionStatus = "planned";
        disruption2.DisruptionType = "test";
        disruption2.PublishedOn = LocalDate.now();
        disruption2.LastUpdated = LocalDate.now();
        disruption2.FromDate = LocalDate.now();
        disruption2.ToDate = LocalDate.now().plusDays(14);
        disruptionsResponse.disruptions.MetroTrain.add(disruption2);

        return disruptionsResponse;
    }

    @Test
    public void checkDisruption_NoDisruptions_ExpectMessageInvokedZeroTimes(){
        given(this.routeService.getAll()).willReturn(Optional.of(mockRoutes()));
        given(this.disruptionService.getDisruption(1)).willReturn(Optional.empty());

        metroAlfred.checkDisruption();
        then(this.messageBot).should(times(0)).send(Mockito.anyString());
    }
}
