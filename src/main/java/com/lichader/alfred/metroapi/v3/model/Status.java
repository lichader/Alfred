package com.lichader.alfred.metroapi.v3.model;

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
