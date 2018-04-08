package com.lichader.alfred.db.model.tracking;

import com.lichader.alfred.db.model.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tracking_parcel", schema = "alfred")
public class TrackingParcel extends BaseEntity {

    @Column(name = "tracking_no", unique = true, nullable = false)
    private String trackingNo;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "delivered", nullable = false)
    private boolean delivered;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // TODO: eager loading can be a performance issue
    @JoinColumn(name = "parcel_id")
    private List<TrackingHistory> histories = new ArrayList<>();

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

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public List<TrackingHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<TrackingHistory> histories) {
        this.histories = histories;
    }
}
