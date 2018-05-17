package com.lichader.alfred.controller.tracking.model;

import com.lichader.alfred.db.model.tracking.TrackingParcel;

import java.util.List;
import java.util.stream.Collectors;

public class TrackingParcelWebModel {
    private String trackingNo;
    private String status;

    private List<TrackingHistoryWebModel> histories;

    private TrackingParcelWebModel(){

    }

    public static TrackingParcelWebModel build(TrackingParcel trackingParcel){
        TrackingParcelWebModel toRet = new TrackingParcelWebModel();
        toRet.trackingNo = trackingParcel.getTrackingNo();
        toRet.status = trackingParcel.getStatus().getDescription();
        toRet.histories = trackingParcel.getHistories()
            .stream().map(TrackingHistoryWebModel::build)
            .collect(Collectors.toList());

        return toRet;
    }

    public String getTrackingNo() {
        return trackingNo;
    }
    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TrackingHistoryWebModel> getHistories() {
        return histories;
    }

    public void setHistories(List<TrackingHistoryWebModel> histories) {
        this.histories = histories;
    }
}
