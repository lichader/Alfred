package com.lichader.alfred.service.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * A model created for binding the json data returned from routes api
 */
public class RouteResponse {

    @JsonProperty("routes")
    public List<Route> Routes;

    @JsonProperty("status")
    public com.lichader.alfred.service.model.v3.Status Status;

    public RouteResponse(){
        Routes = new ArrayList<Route>();
    }

}
