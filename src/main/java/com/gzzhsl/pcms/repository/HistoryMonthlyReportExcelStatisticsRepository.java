package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.HistoryMonthlyReportExcelStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryMonthlyReportExcelStatisticsRepository extends JpaRepository<HistoryMonthlyReportExcelStatistics, String> {
    HistoryMonthlyReportExcelStatistics findByBaseInfo(BaseInfo baseInfo);
}
