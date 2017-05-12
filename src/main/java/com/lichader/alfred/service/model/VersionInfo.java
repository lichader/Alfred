package com.lichader.alfred.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lichader on 13/5/17.
 */
public class VersionInfo {
    @JsonProperty("version")
    public String Version;

    @JsonProperty("health")
    public int Health;
}
