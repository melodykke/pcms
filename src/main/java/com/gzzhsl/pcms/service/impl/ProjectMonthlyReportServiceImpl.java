package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.MonthlyReportVO2MonthlyReport;
import com.gzzhsl.pcms.entity.Project;
import com.gzzhsl.pcms.entity.ProjectMonthlyReport;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.ProjectMonthlyReportRepository;
import com.gzzhsl.pcms.service.ProjectMonthlyReportService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.PathUtil;
import com.gzzhsl.pcms.vo.ProjectMonthlyReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Calendar;

@Service
@Slf4j
public class ProjectMonthlyReportServiceImpl implements ProjectMonthlyReportService{

    @Autowired
    private ProjectMonthlyReportRepository projectMonthlyReportRepository;

    @Override
    @Transactional
    public ProjectMonthlyReport save(ProjectMonthlyReportVO projectMonthlyReportVO) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        Project thisProject = thisUser.getProject();
        // 如果用户对应的Project不存在，则报错
        if (thisProject == null) {
            log.error("【月报错误】 用户对应Project为空，需首先绑定对应的水库工程");
            throw new SysException(SysEnum.MONTHLY_REPORT_ERROR);
        }
        try {
            ProjectMonthlyReport projectMonthlyReport = MonthlyReportVO2MonthlyReport.convert(projectMonthlyReportVO);
            if (projectMonthlyReportVO.getRtFileTempPath() == null || projectMonthlyReportVO.getRtFileTempPath() == "") {
                // 没有上传图片的情况，直接对表格进行存储
                projectMonthlyReport.setProject(thisProject);
                ProjectMonthlyReport projectMonthlyReportRt = projectMonthlyReportRepository.save(projectMonthlyReport);
                return projectMonthlyReportRt;
            } else {
                // 上传图片的情况，需考虑转存
                String rtFileTempPath = projectMonthlyReportVO.getRtFileTempPath();
                projectMonthlyReport.setProject(thisProject);
                ProjectMonthlyReport projectMonthlyReportRt = projectMonthlyReportRepository.save(projectMonthlyReport);
                Calendar cal = Calendar.getInstance();
                cal.setTime(projectMonthlyReportRt.getSubmitDate());
                String date = String.valueOf(cal.get(Calendar.YEAR)) + "/" + String.valueOf(cal.get(Calendar.MONTH) + 1);
                File sourceDestFolder = new File(PathUtil.getFileBasePath(true) + rtFileTempPath);
                String targetDest = PathUtil.getFileBasePath(false) + PathUtil.getMonthlyReportImagePath(thisProject.getPlantName(), date);
                File[] sourceFiles = sourceDestFolder.listFiles();
                if (sourceFiles.length > 0) {
                    for (File sourceFile : sourceFiles) {
                        File targetFile = new File(targetDest + sourceFile.getName());
                        if (!targetFile.exists()) {
                            targetFile.getParentFile().mkdirs();
                        }
                        sourceFile.renameTo(targetFile);
                    }
                }
                return projectMonthlyReportRt;
            }
        } catch (Exception e) {
            log.error("【月报错误】 系统内部错误，可能是JDBC出错");
            throw new SysException(SysEnum.Sys_INNER_ERROR);
        }
    }

}
