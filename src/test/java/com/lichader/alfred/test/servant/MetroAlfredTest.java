package com.lichader.alfred.test.servant;

import com.lichader.alfred.metroapi.v3.DisruptionService;
import com.lichader.alfred.metroapi.v3.RouteService;
import com.lichader.alfred.metroapi.v3.model.*;
import com.lichader.alfred.slack.MessageBot;
import com.lichader.alfred.servant.MetroAlfred;
import com.lichader.alfred.test.AbstractSpringBootTestBase;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@TestPropertySource(properties = {"advanceDaysToCheck=3", "metro.trainLine=South Morang"})
public class MetroAlfredTest extends AbstractSpringBootTestBase {

    @MockBean
    private RouteService routeService;

    @MockBean
    private DisruptionService disruptionService;

    @MockBean
    private MessageBot messageBot;

    @Autowired
    MetroAlfred metroAlfred;

    private final static int expectedRouteId = 2;

    @Test
    public void checkDisruption_TwoDisruptions_ExpectTwoMessageSent(){
        given(this.routeService.getAll()).willReturn(Optional.of(mockRoutes()));
        DisruptionsResponse disruptionsResponse = mockDisruptions();
        given(this.disruptionService.getDisruption(expectedRouteId)).willReturn(Optional.of(disruptionsResponse));

        metroAlfred.checkDisruption();

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        then(this.messageBot).should(times(1)).send(argument.capture());

        String expectedMessageContent = "Disruption: Test disruption 2%0AStatus: planned%0AType: test%0AFrom "
            + disruptionsResponse.disruptions.MetroTrain.get(1).FromDate.toLocalDateTime()
            + " to " + disruptionsResponse.disruptions.MetroTrain.get(1).ToDate.toLocalDateTime();

        Assert.assertEquals(expectedMessageContent, argument.getValue());
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
        inTheFuture.Id = 1;
        inTheFuture.Description = "Test disruption 1";
        inTheFuture.Status = "planned";
        inTheFuture.Type = "test";
        inTheFuture.PublishedOn = ZonedDateTime.now();
        inTheFuture.LastUpdated = ZonedDateTime.now();
        inTheFuture.FromDate = ZonedDateTime.now().plusDays(4);
        inTheFuture.ToDate = ZonedDateTime.now().plusDays(11);

        disruptionsResponse.disruptions.MetroTrain.add(inTheFuture);

        Disruption almostStarted = new Disruption();
        almostStarted.Id = 1;
        almostStarted.Description = "Test disruption 2";
        almostStarted.Status = "planned";
        almostStarted.Type = "test";
        almostStarted.PublishedOn = ZonedDateTime.now();
        almostStarted.LastUpdated = ZonedDateTime.now();
        almostStarted.FromDate = ZonedDateTime.now();
        almostStarted.ToDate = ZonedDateTime.now().plusDays(14);
        disruptionsResponse.disruptions.MetroTrain.add(almostStarted);

        Disruption started = new Disruption();
        started.Id = 2;
        started.Description = "Test disruption 3";
        started.Status = "Started";
        started.Type = "test";
        started.PublishedOn = ZonedDateTime.now();
        started.LastUpdated = ZonedDateTime.now();
        started.FromDate = ZonedDateTime.now().minusDays(1);
        started.ToDate = ZonedDateTime.now().plusDays(14);
        disruptionsResponse.disruptions.MetroTrain.add(started);


        return disruptionsResponse;
    }

    @Test
    public void checkDisruption_NoDisruptions_ExpectMessageInvokedZeroTimes(){
        given(this.routeService.getAll()).willReturn(Optional.of(mockRoutes()));
        given(this.disruptionService.getDisruption(expectedRouteId)).willReturn(Optional.empty());

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
