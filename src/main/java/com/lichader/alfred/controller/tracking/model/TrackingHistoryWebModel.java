package com.lichader.alfred.controller.tracking.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.lichader.alfred.db.model.tracking.TrackingHistory;

import java.time.LocalDateTime;

public class TrackingHistoryWebModel {

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime time;

    private String location;
    private String status;

    private TrackingHistoryWebModel(){

    }

    public static TrackingHistoryWebModel build(TrackingHistory trackingHistory){
        TrackingHistoryWebModel toRet = new TrackingHistoryWebModel();
        toRet.setTime(trackingHistory.getTime());
        toRet.setLocation(trackingHistory.getLocation());
        toRet.setStatus(trackingHistory.getStatus());

        return  toRet;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
