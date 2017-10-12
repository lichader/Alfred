package com.lichader.alfred.metroapi.v3;

import com.lichader.alfred.metroapi.v3.model.RouteResponse;
import org.springframework.stereotype.Service;

@Service
public class RouteService extends MetroService{

    public final static String RESOURCE_ROUTES = "routes";

    public RouteResponse getAll(){
        return getResource(RESOURCE_ROUTES, RouteResponse.class);
    }
}
