package com.lichader.alfred.controller.tracking.model;

public class CreateNewTrackingsWebRequest {

    private String trackingNo;
    private String destination;

    public CreateNewTrackingsWebRequest() {
        this.destination = "中国";
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
