package com.lichader.alfred.service.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lichader on 14/6/17.
 */
public class DisruptionRoute {

    @JsonProperty("route_type")
    public int Routetype;

    @JsonProperty("route_id")
    public int RouteId;

    @JsonProperty("route_name")
    public String RouteName;

    @JsonProperty("route_number")
    public String RouteNumber;

    @JsonProperty("direction")
    public DisruptionDirection Direction;
}
