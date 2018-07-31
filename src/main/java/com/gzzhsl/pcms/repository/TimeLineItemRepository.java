package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.TimeLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeLineItemRepository extends JpaRepository<TimeLineItem, String>{
    TimeLineItem findByTimeLineItemId(int id);
}
