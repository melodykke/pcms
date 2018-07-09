package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.PreProgressImg;
import com.gzzhsl.pcms.repository.PreProgressImgRepository;
import com.gzzhsl.pcms.service.PreProgressImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class PreProgressImgServiceImpl implements PreProgressImgService {
    @Autowired
    private PreProgressImgRepository preProgressImgRepository;
    @Override
    public PreProgressImg getByPreProgressImgId(String preProgressImgId) {
        return preProgressImgRepository.findByPreProgressImgId(preProgressImgId);
    }
}
