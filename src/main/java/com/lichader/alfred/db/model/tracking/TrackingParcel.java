package com.lichader.alfred.db.model.tracking;

import com.lichader.alfred.db.model.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tracking_parcel")
public class TrackingParcel extends BaseEntity {

    @Column(name = "tracking_no", unique = true, nullable = false)
    private String trackingNo;

    @OneToOne(fetch = FetchType.EAGER)
    private TrackingStatus status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // TODO: eager loading can be a performance issue
    @JoinColumn(name = "parcel_id")
    private List<TrackingHistory> histories = new ArrayList<>();

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public TrackingStatus getStatus() {
        return status;
    }

    public void setStatus(TrackingStatus status) {
        this.status = status;
    }

    public List<TrackingHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<TrackingHistory> histories) {
        this.histories = histories;
    }
}
