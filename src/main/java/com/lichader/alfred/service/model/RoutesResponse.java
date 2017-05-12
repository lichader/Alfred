package com.lichader.alfred.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * A model created for binding the json data returned from routes api
 */
public class RoutesResponse {

    @JsonProperty("routes")
    public List<Route> Routes;

    @JsonProperty("status")
    public VersionInfo Status;

    public RoutesResponse(){
        Routes = new ArrayList<Route>();
    }

    public static class Route{

        @JsonProperty("route_type")
        public String RouteType;

        @JsonProperty("route_id")
        public int RouteId;

        @JsonProperty("route_name")
        public String RouteName;

        @JsonProperty("route_number")
        public String RouteNumber;
    }
}
