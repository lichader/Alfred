package com.lichader.alfred.db.model.tracking;

import com.lichader.alfred.db.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tracking_status")
public class TrackingStatus extends BaseEntity {

    @Column
    private int code;

    @Column
    private String description;

    // Used by hibernate
    public TrackingStatus(){

    }

    public TrackingStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
