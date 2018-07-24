package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.AnnualInvestment;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.vo.AnnualInvestmentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnnualInvestmentService {
    AnnualInvestment save(AnnualInvestmentVO annualInvestmentVO);
    boolean containsSameYear(String year);
    Page<AnnualInvestment> findByState(Pageable pageable, byte state);
    List<AnnualInvestment> getByProject(BaseInfo baseInfo);
    AnnualInvestmentVO getById(String id);
    List<AnnualInvestment> getByYearAndProject(String year, BaseInfo baseInfo);
    AnnualInvestment findById(String id);
    Feedback approveAnnualInvestment(UserInfo thisUser, Boolean switchState, String checkinfo, AnnualInvestment thisAnnualInvestment);
}
