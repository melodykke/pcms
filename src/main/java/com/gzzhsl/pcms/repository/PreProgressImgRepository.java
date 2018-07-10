package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.PreProgress;
import com.gzzhsl.pcms.entity.PreProgressImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreProgressImgRepository extends JpaRepository<PreProgressImg, String> {
    PreProgressImg findByPreProgressImgId(String preProgressId);
    List<PreProgressImg> deleteByPreProgress(PreProgress preProgress);
}
