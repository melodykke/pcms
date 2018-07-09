package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.PreProgressImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreProgressImgRepository extends JpaRepository<PreProgressImg, String> {
    PreProgressImg findByPreProgressImgId(String preProgressId);
}
