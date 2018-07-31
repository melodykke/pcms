package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.TimeLineItem;
import com.gzzhsl.pcms.repository.TimeLineItemRepository;
import com.gzzhsl.pcms.service.TimeLineItemService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class TimeLineItemServiceImpl implements TimeLineItemService {

    @Autowired
    private TimeLineItemRepository timeLineItemRepository;

    @Override
    public TimeLineItem getById(int id) {
        return timeLineItemRepository.findByTimeLineItemId(id);
    }
}
