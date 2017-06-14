package com.lichader.alfred.metroapi.v3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lichader on 14/6/17.
 */
public class Route {
    @JsonProperty("route_type")
    public String RouteType;

    @JsonProperty("route_id")
    public int RouteId;

    @JsonProperty("route_name")
    public String RouteName;

    @JsonProperty("route_number")
    public String RouteNumber;
}
