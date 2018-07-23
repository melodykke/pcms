package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.Tender;
import com.gzzhsl.pcms.entity.TenderImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenderImgRepository extends JpaRepository<TenderImg, String> {
    TenderImg findOneByTenderImgId(String tenderImgId);
    List<TenderImg> deleteByTender(Tender tender);
}
