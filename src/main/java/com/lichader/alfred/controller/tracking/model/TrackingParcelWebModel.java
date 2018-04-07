package com.lichader.alfred.controller.tracking.model;

import com.lichader.alfred.db.model.tracking.TrackingParcel;

import java.util.List;
import java.util.stream.Collectors;

public class TrackingParcelWebModel {
    private String trackingNo;
    private String destination;
    private boolean delivered;

    private List<TrackingHistoryWebModel> histories;

    public String getTrackingNo() {
        return trackingNo;
    }

    private TrackingParcelWebModel(){

    }

    public static TrackingParcelWebModel build(TrackingParcel trackingParcel){
        TrackingParcelWebModel toRet = new TrackingParcelWebModel();
        toRet.trackingNo = trackingParcel.getTrackingNo();
        toRet.destination = trackingParcel.getDestination();
        toRet.delivered = trackingParcel.isDelivered();
        toRet.histories = trackingParcel.getHistories()
            .stream().map(TrackingHistoryWebModel::build)
            .collect(Collectors.toList());

        return toRet;
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

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public List<TrackingHistoryWebModel> getHistories() {
        return histories;
    }

    public void setHistories(List<TrackingHistoryWebModel> histories) {
        this.histories = histories;
    }
}
