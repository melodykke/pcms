package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Tender;
import com.gzzhsl.pcms.entity.TenderImg;

import java.util.List;

public interface TenderImgService {
    TenderImg getByTenderImgId(String tenderImgId);
    List<TenderImg> deleteByTender(Tender tender);
}
