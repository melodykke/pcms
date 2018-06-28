package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.repository.FeedbackRepository;
import com.gzzhsl.pcms.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@Slf4j
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getFeedbackByUserId(String userId) {
        List<Feedback> feedbacks = null;
        Specification querySpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get("userId"), userId);
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        feedbacks = feedbackRepository.findAll(querySpecification, sort);
        return feedbacks;
    }
}
