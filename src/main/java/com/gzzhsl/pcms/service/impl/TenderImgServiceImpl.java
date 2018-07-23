package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.Tender;
import com.gzzhsl.pcms.entity.TenderImg;
import com.gzzhsl.pcms.repository.TenderImgRepository;
import com.gzzhsl.pcms.service.TenderImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class TenderImgServiceImpl implements TenderImgService {
    @Autowired
    private TenderImgRepository tenderImgRepository;

    @Override
    public TenderImg getByTenderImgId(String tenderImgId) {
        return tenderImgRepository.findOneByTenderImgId(tenderImgId);
    }

    @Override
    public List<TenderImg> deleteByTender(Tender tender) {
        return tenderImgRepository.deleteByTender(tender);
    }
}
