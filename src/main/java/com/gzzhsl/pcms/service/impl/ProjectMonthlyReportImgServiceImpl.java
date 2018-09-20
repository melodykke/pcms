package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.mapper.ProjectMonthlyReportImgMapper;
import com.gzzhsl.pcms.model.ProjectMonthlyReportImg;
import com.gzzhsl.pcms.service.ProjectMonthlyReportImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectMonthlyReportImgServiceImpl implements ProjectMonthlyReportImgService {

    @Autowired
    private ProjectMonthlyReportImgMapper projectMonthlyReportImgMapper;


    @Override
    public ProjectMonthlyReportImg findById(String id) {
        return projectMonthlyReportImgMapper.selectByPrimaryKey(id);
    }

    @Override
    public int batchSave(List<ProjectMonthlyReportImg> projectMonthlyReportImgs) {
        return projectMonthlyReportImgMapper.batchInsert(projectMonthlyReportImgs);
    }
}
