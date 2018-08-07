package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.Tender;
import com.gzzhsl.pcms.vo.TenderVO;
import org.springframework.beans.BeanUtils;

public class Tender2VO {
    public static TenderVO convert(Tender tender) {
        TenderVO tenderVO = new TenderVO();
        BeanUtils.copyProperties(tender, tenderVO);
        tenderVO.setBaseInfo(null);
        return tenderVO;
    }
}
