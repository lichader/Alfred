package com.lichader.alfred.db.model.tracking;

import com.lichader.alfred.db.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tracking_history")
public class TrackingHistory extends BaseEntity {

    @Column
    private LocalDateTime time;

    @Column
    private String location;

    @Column
    private String status;

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
