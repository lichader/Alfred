package com.lichader.alfred.service.model.v3;

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
    public com.lichader.alfred.service.model.v3.Status Status;

}
