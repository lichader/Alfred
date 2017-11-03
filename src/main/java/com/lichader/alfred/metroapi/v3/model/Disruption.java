package com.lichader.alfred.metroapi.v3.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lichader on 14/6/17.
 */
public class Disruption {

    @JsonProperty("disruption_id")
    public int Id;

    @JsonProperty("title")
    public String Title;

    @JsonProperty("url")
    public String Url;

    @JsonProperty("description")
    public String Description;

    @JsonProperty("disruption_status")
    public String Status;

    @JsonProperty("disruption_type")
    public String Type;

    @JsonProperty("published_on")
    public ZonedDateTime PublishedOn;

    @JsonProperty("last_updated")
    public ZonedDateTime LastUpdated;

    @JsonProperty("from_date")
    public ZonedDateTime FromDate;

    @JsonProperty("to_date")
    public ZonedDateTime ToDate;

    @JsonProperty("routes")
    public List<DisruptionRoute> Routes;

    public Disruption(){
        Routes = new ArrayList<>();
    }
}
