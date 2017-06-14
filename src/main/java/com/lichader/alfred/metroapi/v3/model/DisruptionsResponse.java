package com.lichader.alfred.metroapi.v3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lichader on 14/6/17.
 */
public class DisruptionsResponse {
    @JsonProperty("disruptions")
    public Disruptions disruptions;

    @JsonProperty("status")
    public Status status;
}
