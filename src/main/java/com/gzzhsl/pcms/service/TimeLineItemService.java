package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.model.TimeLineItem;

public interface TimeLineItemService {
    TimeLineItem findById(int id);

    TimeLineItem findLatestOne();
}
