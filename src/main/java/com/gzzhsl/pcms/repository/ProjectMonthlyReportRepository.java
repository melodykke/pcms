package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectMonthlyReportRepository extends JpaRepository<ProjectMonthlyReport, String>
        , JpaSpecificationExecutor<ProjectMonthlyReport> {
    ProjectMonthlyReport findByPId(String pId);
}
