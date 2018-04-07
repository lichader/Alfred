package com.lichader.alfred.controller.tracking.model;

import com.lichader.alfred.db.model.tracking.TrackingHistory;

import java.time.LocalDateTime;

public class TrackingHistoryWebModel {
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
