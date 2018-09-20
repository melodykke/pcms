package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.model.ProjectMonthlyReportImg;

import java.util.List;

public interface ProjectMonthlyReportImgService {
    ProjectMonthlyReportImg findById(String id);
    int batchSave(List<ProjectMonthlyReportImg> projectMonthlyReportImgs);
}
