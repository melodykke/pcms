package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, String>, JpaSpecificationExecutor<ProjectStatus> {
}
