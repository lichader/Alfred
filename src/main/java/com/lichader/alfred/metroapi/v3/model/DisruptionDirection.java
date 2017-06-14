package com.lichader.alfred.metroapi.v3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lichader on 14/6/17.
 */
public class DisruptionDirection {

    @JsonProperty("route_direction_id")
    public int RouteDirectionId;

    @JsonProperty("direction_id")
    public int DirectionId;

    @JsonProperty("direction_name")
    public String DirectionName;

    @JsonProperty("service_time")
    public String ServiceTime;
}
