package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    List<Region> findByParentId(Integer parentId);
    List<Region> findByParentIdNotIn(List<Integer> notInList);
}
