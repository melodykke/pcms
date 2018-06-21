package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.ProjectMonthlyReportImg;
import com.gzzhsl.pcms.repository.ProjectMonthlyReportImgRepository;
import com.gzzhsl.pcms.service.ProjectMonthlyReportImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectMonthlyReportImgServiceImpl implements ProjectMonthlyReportImgService {
    @Autowired
    private ProjectMonthlyReportImgRepository projectMonthlyReportImgRepository;
    @Override
    public ProjectMonthlyReportImg getById(String id) {
        return projectMonthlyReportImgRepository.findByProjectMonthlyReportImgId(id);
    }
}
