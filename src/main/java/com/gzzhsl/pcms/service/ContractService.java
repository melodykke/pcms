package com.gzzhsl.pcms.service;

import com.github.pagehelper.PageInfo;
import com.gzzhsl.pcms.model.Contract;
import com.gzzhsl.pcms.model.Feedback;
import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.vo.ContractVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContractService {

    Contract findById(String id);
    PageInfo<Contract> findPageByState(byte state, int pageNum, int pageSize);
    Contract findWithImgById(String id);




    Boolean hasInnerContract();
    Contract save(ContractVO contractVO);
    Feedback approveContract(UserInfo thisUser, Boolean switchState, String checkinfo, Contract thisContract);
}
