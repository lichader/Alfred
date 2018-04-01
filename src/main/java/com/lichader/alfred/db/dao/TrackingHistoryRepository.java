package com.lichader.alfred.db.dao;

import com.lichader.alfred.db.model.tracking.TrackingHistory;
import org.springframework.data.repository.CrudRepository;

public interface TrackingHistoryRepository extends CrudRepository<TrackingHistory, Long> {
}
