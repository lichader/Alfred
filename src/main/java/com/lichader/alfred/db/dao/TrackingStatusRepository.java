package com.lichader.alfred.db.dao;

import com.lichader.alfred.db.model.tracking.TrackingStatus;
import org.springframework.data.repository.CrudRepository;

public interface TrackingStatusRepository extends CrudRepository<TrackingStatus, Long> {
    TrackingStatus findOneByCode(int code);
}
