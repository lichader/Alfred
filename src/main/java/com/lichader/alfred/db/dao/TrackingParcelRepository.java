package com.lichader.alfred.db.dao;


import com.lichader.alfred.db.model.tracking.TrackingParcel;
import com.lichader.alfred.db.model.tracking.TrackingStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrackingParcelRepository extends CrudRepository<TrackingParcel, Long> {

    TrackingParcel findOneByTrackingNoIgnoreCase(String trackingNo);

    List<TrackingParcel> findByStatus(TrackingStatus status);
    List<TrackingParcel> findByStatusNot(TrackingStatus status);
}
