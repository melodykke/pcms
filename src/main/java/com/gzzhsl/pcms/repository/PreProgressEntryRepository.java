package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.entity.PreProgress;
import com.gzzhsl.pcms.entity.PreProgressEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreProgressEntryRepository extends JpaRepository<PreProgressEntry, String> {
    List<PreProgressEntry> deleteByPreProgress(PreProgress preProgress);
}
