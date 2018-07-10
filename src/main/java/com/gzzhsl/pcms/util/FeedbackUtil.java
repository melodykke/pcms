package com.gzzhsl.pcms.util;

import com.gzzhsl.pcms.entity.Feedback;

import java.util.Date;

public class FeedbackUtil {
    public static Feedback buildFeedback(String baseInfoId, String checker,String type, String targetReportId, Date time, String msg, byte state) {
        Feedback feedback = new Feedback();
        feedback.setType(type);
        feedback.setTargetId(targetReportId);
        feedback.setCreateTime(time);
        feedback.setMsg(msg);
        feedback.setState(state);
        feedback.setChecked(false);
        feedback.setBaseInfoId(baseInfoId);
        feedback.setChecker(checker);
        return feedback;
    }
}
