package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.repository.FeedbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FeedbackService {
    Feedback save(Feedback feedback);
    List<Feedback> getFeedbackByUserId(String userId);
}
