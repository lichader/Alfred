package com.lichader.alfred.service.model.v3;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lichader on 14/6/17.
 */
public class Disruption {

    @JsonProperty("disruption_id")
    public int Disruption_id;

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
//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
    public Date PublishedOn;

    @JsonProperty("last_updated")
    public Date LastUpdated;

    @JsonProperty("from_date")
    public Date FromDate;

    @JsonProperty("to_date")
    public Date ToDate;

    @JsonProperty("routes")
    public List<DisruptionRoute> Routes;
}
