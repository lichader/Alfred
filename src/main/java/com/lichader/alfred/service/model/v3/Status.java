package com.lichader.alfred.service.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lichader on 13/5/17.
 */
public class Status {
    @JsonProperty("version")
    public String Version;

    @JsonProperty("health")
    public int Health;
}
