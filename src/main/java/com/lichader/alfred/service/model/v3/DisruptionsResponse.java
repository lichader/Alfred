package com.lichader.alfred.service.model.v3;

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
