package com.lichader.alfred.db.dao;


import com.lichader.alfred.db.model.tracking.TrackingParcel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrackingParcelRepository extends CrudRepository<TrackingParcel, Long> {
    List<TrackingParcel> findByTrackingNoIgnoreCase(String trackingNo);
}
