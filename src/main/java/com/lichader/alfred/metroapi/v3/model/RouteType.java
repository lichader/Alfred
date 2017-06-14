package com.lichader.alfred.metroapi.v3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lichader on 14/6/17.
 */
public class RouteType{

    @JsonProperty("route_type_name")
    public String Name;

    @JsonProperty("route_type")
    public int Code;
}
