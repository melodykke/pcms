package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.ProjectStatus;
import com.gzzhsl.pcms.entity.TimeLineItem;
import com.gzzhsl.pcms.repository.TimeLineItemRepository;
import com.gzzhsl.pcms.service.ProjectStatusService;
import com.gzzhsl.pcms.service.TimeLineItemService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class TimeLineItemServiceImpl implements TimeLineItemService {

    @Autowired
    private TimeLineItemRepository timeLineItemRepository;
    @Autowired
    private ProjectStatusService projectStatusService;

    @Override
    public TimeLineItem getById(int id) {
        return timeLineItemRepository.findByTimeLineItemId(id);
    }

    @Override
    public TimeLineItem getLatestOne() {
        List<ProjectStatus> projectStatuses = projectStatusService.getProjectStatus();
        if (projectStatuses == null || projectStatuses.size() == 0) {
            return null;
        }
        ProjectStatus lastProjectStatus = projectStatuses.get(projectStatuses.size()-1);
        int timeLineItemId = lastProjectStatus.getTimeLineItem().getTimeLineItemId();
        return timeLineItemRepository.findByTimeLineItemId(timeLineItemId);
    }
}
