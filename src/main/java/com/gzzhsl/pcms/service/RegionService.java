package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Region;

import java.util.List;

public interface RegionService {
    List<Region> getChildrenRegion(String parentId);
}
