package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.model.Region;

import java.util.List;

public interface RegionService {
    List<Region> getAllRootRegion();

    List<Region> getChildrenRegion(Integer parentId);
}
