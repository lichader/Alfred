package com.lichader.alfred.metroapi.v3.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lichader on 14/6/17.
 */
public class Disruption {

    @JsonProperty("disruption_id")
    public int DisruptionId;

    @JsonProperty("title")
    public String Title;

    @JsonProperty("url")
    public String Url;

    @JsonProperty("description")
    public String Description;

    @JsonProperty("disruption_status")
    public String DisruptionStatus;

    @JsonProperty("disruption_type")
    public String DisruptionType;

    @JsonProperty("published_on")
    public LocalDate PublishedOn;

    @JsonProperty("last_updated")
    public LocalDate LastUpdated;

    @JsonProperty("from_date")
    public LocalDate FromDate;

    @JsonProperty("to_date")
    public LocalDate ToDate;

    @JsonProperty("routes")
    public List<DisruptionRoute> Routes;

    public Disruption(){
        Routes = new ArrayList<>();
    }
}
