package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.Region;
import com.gzzhsl.pcms.repository.RegionRepository;
import com.gzzhsl.pcms.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@Slf4j
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionRepository regionRepository;

    @Override
    public List<Region> getAllRootRegion() {
        List<Integer> notInList = Arrays.asList(1);
        return regionRepository.findByParentIdNotIn(notInList);
    }

    @Override
    public List<Region> getChildrenRegion(Integer parentId) {
        return regionRepository.findByParentId(parentId);
    }
}
