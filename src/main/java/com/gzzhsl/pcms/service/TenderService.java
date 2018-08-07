package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.entity.Tender;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.vo.TenderVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenderService {
    Tender save(TenderVO tenderVO);
    Page<TenderVO> findByState(Pageable pageable, byte state);
    TenderVO getById(String id);
    Tender findById(String id);
    Feedback approveTender(UserInfo userInfo, boolean switchState, String checkInfo, Tender tender);
}
