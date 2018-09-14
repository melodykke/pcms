package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.Feedback;
import java.util.List;

public interface FeedbackMapper {
    int deleteByPrimaryKey(String feedbackId);

    int insert(Feedback record);

    Feedback selectByPrimaryKey(String feedbackId);

    List<Feedback> selectAll();

    int updateByPrimaryKey(Feedback record);
}