package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.Feedback;

import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.FeedbackService;

import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.util.TimeUtil;
import com.gzzhsl.pcms.vo.OverallFeedbackVO;

import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/feedback")
@Slf4j
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private UserService userService;

    @GetMapping("/tofeedback")
    public String toFeedback() {
        return "feedback";
    }

    @GetMapping("/getoverallfeedback")
    @ResponseBody
    public ResultVO getOverallFeedback() {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        if (thisUser == null || thisUser.getUserId() == null || thisUser.getUserId() == "") {
            log.error("【消息错误】未读取到用户信息");
            throw new SysException(SysEnum.SIGNIN_PARAM_ERROR);
        }
        if (thisProject == null || thisProject.getBaseInfoId() == null || thisProject.getBaseInfoId() == "") {
            log.error("【消息错误】未读取到该账户下的水库信息");
            throw new SysException(SysEnum.ACCOUNT_NO_PROJECT);
        }
        List<Feedback> feedbacks = feedbackService.getAllUnchecked(thisProject.getBaseInfoId()); // 总的未检视的消息
        List<Feedback> monthlyReportFeedbacks = new ArrayList<>(); // 月报的消息
        List<Feedback> projectBasicInfoFeedbacks = new ArrayList<>(); // 项目基础信息的消息
        List<Feedback> projectPreProgressFeedbacks = new ArrayList<>(); // 项目基础信息的消息
        List<Feedback> contractFeedbacks = new ArrayList<>(); // 项目基础信息的消息
        /*如果有其他，继续往这儿加*/
        for (Feedback feedback : feedbacks) {
            if ("月报".equals(feedback.getType())) {
                monthlyReportFeedbacks.add(feedback);
            } else if ("项目基本信息".equals(feedback.getType())) {
                projectBasicInfoFeedbacks.add(feedback);
            } else if ("项目前期信息".equals(feedback.getType())) {
                projectPreProgressFeedbacks.add(feedback);
            } else if ("合同备案信息".equals(feedback.getType())) {
                contractFeedbacks.add(feedback);
            }/*如果有其他，继续往这儿加*/
        }
        OverallFeedbackVO overallFeedbackVO = new OverallFeedbackVO();
        Map<String, String> article = new HashMap<>();
        overallFeedbackVO.setAllUncheckedNum(feedbacks.size());
        if (monthlyReportFeedbacks.size() > 0) {
            article.put("月报新消息", TimeUtil.getDatePoor(new Date(), monthlyReportFeedbacks.get(0).getCreateTime()));
        }
        if (projectBasicInfoFeedbacks.size() > 0) {
            article.put("基础信息消息", TimeUtil.getDatePoor(new Date(), projectBasicInfoFeedbacks.get(0).getCreateTime()));
        }
        if (projectPreProgressFeedbacks.size() > 0) {
            article.put("前期信息消息", TimeUtil.getDatePoor(new Date(), projectPreProgressFeedbacks.get(0).getCreateTime()));
        }
        if (contractFeedbacks.size() > 0) {
            article.put("合同备案信息", TimeUtil.getDatePoor(new Date(), contractFeedbacks.get(0).getCreateTime()));
        }/*如果有其他，继续往这儿加*/
        overallFeedbackVO.setArticle(article);
        return ResultUtil.success(overallFeedbackVO);
    }

    @GetMapping("/changetochecked")
    @ResponseBody
    public ResultVO changeToChecked(String feedbackId) {
        Feedback feedback = feedbackService.getById(feedbackId);
        feedback.setChecked(true);
        feedbackService.save(feedback);
        return ResultUtil.success();
    }

    @GetMapping("/queryfeedback")
    @ResponseBody
    public Page<Feedback> queryFeedback(@RequestParam(required = false, name = "pageSize", defaultValue = "15") Integer pageSize,
                                       @RequestParam(required = false, name = "startIndex") Integer startIndex,
                                       @RequestParam(required = false, name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                       @RequestParam(required = false, name = "type", defaultValue = "") String type){
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        if (thisProject == null || thisProject.getBaseInfoId() == null || thisProject.getBaseInfoId() == "") {
            log.error("【通知错误】未读取到该账户下的水库信息");
            throw new SysException(SysEnum.ACCOUNT_NO_PROJECT);
        }
        Integer page = pageIndex;
        Integer size = pageSize;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Page<Feedback> feedbacks = feedbackService.findAllByType(pageRequest, thisProject.getBaseInfoId(), type);
        return feedbacks;
    }
}
