package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectMonthlyReportRepository extends JpaRepository<ProjectMonthlyReport, String>
        , JpaSpecificationExecutor<ProjectMonthlyReport> {
    ProjectMonthlyReport findByProjectMonthlyReportId(String projectMonthlyReportId); // TODO 这里需要指出是哪个水库的月报

    /*  List<ProjectMonthlyReport> findByProjectAndState(Project project, Byte state);*/
    List<ProjectMonthlyReport> findAllByBaseInfo(BaseInfo baseInfo);
}
