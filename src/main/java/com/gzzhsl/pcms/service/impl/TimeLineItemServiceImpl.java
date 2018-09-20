package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.mapper.TimeLineItemMapper;
import com.gzzhsl.pcms.model.ProjectStatus;
import com.gzzhsl.pcms.model.TimeLineItem;
import com.gzzhsl.pcms.repository.TimeLineItemRepository;
import com.gzzhsl.pcms.service.ProjectStatusService;
import com.gzzhsl.pcms.service.TimeLineItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class TimeLineItemServiceImpl implements TimeLineItemService {

    @Autowired
    private TimeLineItemMapper timeLineItemMapper;
    @Autowired
    private ProjectStatusService projectStatusService;

    @Override
    public TimeLineItem findById(int id) {
        return timeLineItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public TimeLineItem findLatestOne() {
        List<ProjectStatus> projectStatuses = projectStatusService.findThisProjectStatus();
        if (projectStatuses == null || projectStatuses.size() == 0) {
            return null;
        }
        ProjectStatus lastProjectStatus = projectStatuses.get(projectStatuses.size()-1);
        int timeLineItemId = lastProjectStatus.getTimeLineItemId();
        return timeLineItemMapper.selectByPrimaryKey(timeLineItemId);
    }
}
