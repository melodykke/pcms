package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.ProjectStatus;
import com.gzzhsl.pcms.entity.TimeLineItem;

import java.util.List;

public interface TimeLineItemService {
    TimeLineItem getById(int id);
    TimeLineItem getLatestOne();
}
