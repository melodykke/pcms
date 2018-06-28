package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface FeedbackService {
    Feedback save(Feedback feedback);
    List<Feedback> getFeedbackByUserId(String userId);
}
