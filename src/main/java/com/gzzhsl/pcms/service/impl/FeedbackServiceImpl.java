package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.repository.FeedbackRepository;
import com.gzzhsl.pcms.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

  /*  @Override
    public List<Feedback> getByTypeId(String typeId) {
        return feedbackRepository.findByTypeId(typeId);
    }*/

    @Override
    public List<Feedback> getAll() {
        return null;
    }

    @Override
    public Feedback getById(String feedbackId) {
        return feedbackRepository.findOne(feedbackId);
    }

    @Override
    public List<Feedback> getByBaseInfoId(String baseInfoId) {
        List<Feedback> feedbacks = null;
        Specification querySpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Predicate predicate = cb.equal(root.get("baseInfoId"), baseInfoId);
                return predicate;
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return feedbackRepository.findAll(querySpecification, sort);
    }

    @Override
    public List<Feedback> getAllUnchecked(String baseInfoId) {
        Boolean checked = false;
        Specification querySpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(baseInfoId)) {
                    predicates.add(cb.equal(root.get("baseInfoId"), baseInfoId));
                }
                predicates.add(cb.equal(root.get("checked"), checked));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return feedbackRepository.findAll(querySpecification, sort);
    }

    @Override
    public Page<Feedback> findAllByType(Pageable pageable, String baseInfoId, String type) {
        Specification querySpecification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(baseInfoId)) {
                    predicates.add(cb.equal(root.get("baseInfoId"), baseInfoId));
                }
                if (StringUtils.isNotBlank(type)) {
                    predicates.add(cb.equal(root.get("type"), type));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return feedbackRepository.findAll(querySpecification, pageable);
    }
}
