package com.gzzhsl.pcms.service;

import com.github.pagehelper.PageInfo;
import com.gzzhsl.pcms.model.AnnualInvestment;
import com.gzzhsl.pcms.model.BaseInfo;
import com.gzzhsl.pcms.model.Feedback;
import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.vo.AnnualInvestmentVO;
import java.util.List;

public interface AnnualInvestmentService {
    AnnualInvestment managerFindByYearAndProject(String baseInfoId, String year);
    List<AnnualInvestment> getByProjectId(String baseInfoId);
    PageInfo<AnnualInvestment> findPageByState(byte state, int pageNum, int pageSize);



    AnnualInvestment save(AnnualInvestmentVO annualInvestmentVO);
    boolean containsSameYear(String year);
    AnnualInvestmentVO getById(String id);
    List<AnnualInvestment> getByYearAndProject(String year, BaseInfo baseInfo);
    AnnualInvestment findById(String id);
    Feedback approveAnnualInvestment(UserInfo thisUser, Boolean switchState, String checkinfo, AnnualInvestment thisAnnualInvestment);

    List<AnnualInvestmentVO> getAllApprovedAnnualInvestment();
}
