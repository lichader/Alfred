package com.lichader.alfred.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for binding the data returned from route_types resource in Metro API
 */
public class RouteTypesResponse {

    @JsonProperty("route_types")
    public List<RouteType> RouteTypes;

    @JsonProperty("status")
    public VersionInfo Status;

    public RouteTypesResponse(){
        RouteTypes = new ArrayList<RouteType>();
    }


    public static class RouteType{

        @JsonProperty("route_type_name")
        public String Name;

        @JsonProperty("route_type")
        public int Code;
    }


}
