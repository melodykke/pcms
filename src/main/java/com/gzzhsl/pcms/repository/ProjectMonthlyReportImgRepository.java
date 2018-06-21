package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.ProjectMonthlyReportImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMonthlyReportImgRepository extends JpaRepository<ProjectMonthlyReportImg, String> {
    ProjectMonthlyReportImg findByProjectMonthlyReportImgId(String id);
}
