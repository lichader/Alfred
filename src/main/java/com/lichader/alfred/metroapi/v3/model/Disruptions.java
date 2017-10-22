package com.lichader.alfred.metroapi.v3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichader on 14/6/17.
 */
public class Disruptions {

    @JsonProperty("general")
    public List<Disruption> General;

    @JsonProperty("metro_train")
    public List<Disruption> MetroTrain;

    @JsonProperty("metro_tram")
    public List<Disruption> MetroTram;

    @JsonProperty("metro_bus")
    public List<Disruption> MetroBus;

    @JsonProperty("regional_train")
    public List<Disruption> RegionalTrain;

    @JsonProperty("regional_coach")
    public List<Disruption> RegionalCoach;

    @JsonProperty("regional_bus")
    public List<Disruption> RegionalBus;

    public Disruptions(){
        General = new ArrayList<>();
        MetroTrain = new ArrayList<>();
        MetroTram = new ArrayList<>();
        MetroBus = new ArrayList<>();
        RegionalTrain = new ArrayList<>();
        RegionalCoach = new ArrayList<>();
        RegionalBus = new ArrayList<>();
    }
}
