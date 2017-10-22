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
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@TestPropertySource(properties = "advanceDaysToCheck=3")
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
        then(this.messageBot).should(times(1)).send(Mockito.anyString());
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

        Disruption inTheFuture = new Disruption();
        inTheFuture.DisruptionId = 1;
        inTheFuture.Description = "Test disruption 1";
        inTheFuture.DisruptionStatus = "planned";
        inTheFuture.DisruptionType = "test";
        inTheFuture.PublishedOn = LocalDate.now();
        inTheFuture.LastUpdated = LocalDate.now();
        inTheFuture.FromDate = LocalDate.now().plusDays(4);
        inTheFuture.ToDate = LocalDate.now().plusDays(11);

        disruptionsResponse.disruptions.MetroTrain.add(inTheFuture);

        Disruption almostStarted = new Disruption();
        almostStarted.DisruptionId = 1;
        almostStarted.Description = "Test disruption 2";
        almostStarted.DisruptionStatus = "planned";
        almostStarted.DisruptionType = "test";
        almostStarted.PublishedOn = LocalDate.now();
        almostStarted.LastUpdated = LocalDate.now();
        almostStarted.FromDate = LocalDate.now();
        almostStarted.ToDate = LocalDate.now().plusDays(14);
        disruptionsResponse.disruptions.MetroTrain.add(almostStarted);

        Disruption started = new Disruption();
        started.DisruptionId = 2;
        started.Description = "Test disruption 3";
        started.DisruptionStatus = "Started";
        started.DisruptionType = "test";
        started.PublishedOn = LocalDate.now();
        started.LastUpdated = LocalDate.now();
        started.FromDate = LocalDate.now().minusDays(1);
        started.ToDate = LocalDate.now().plusDays(14);
        disruptionsResponse.disruptions.MetroTrain.add(started);


        return disruptionsResponse;
    }

    @Test
    public void checkDisruption_NoDisruptions_ExpectMessageInvokedZeroTimes(){
        given(this.routeService.getAll()).willReturn(Optional.of(mockRoutes()));
        given(this.disruptionService.getDisruption(1)).willReturn(Optional.empty());

        metroAlfred.checkDisruption();
        then(this.messageBot).should(times(0)).send(Mockito.anyString());
    }

    @Test
    public void checkDisruption_NoRoutesFound_ExpectMessageInvokedZeroTimes(){
        given(this.routeService.getAll()).willReturn(Optional.empty());

        metroAlfred.checkDisruption();
        then(this.disruptionService).should(times(0)).getDisruption(Mockito.anyInt());
        then(this.messageBot).should(times(0)).send(Mockito.anyString());
    }
}
