package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface FeedbackService {
    int save(Feedback feedback);








    List<Feedback> getFeedbackByUserId(String userId);
/*    List<Feedback> getByTypeId(String typeId);*/
    List<Feedback> getAll();
    Feedback getById(String feedbackId);
    List<Feedback> getByBaseInfoId(String baseInfoId);
    List<Feedback> getAllUnchecked(String baseInfoId);
    Page<Feedback> findAllByType(Pageable pageable, String baseInfoId, String type);
}
