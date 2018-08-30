package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.Notification;
import com.gzzhsl.pcms.entity.OperationLog;
import com.gzzhsl.pcms.enums.NotificationTypeEnum;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.BaseInfoService;
import com.gzzhsl.pcms.service.NotificationService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.util.TimeUtil;
import com.gzzhsl.pcms.vo.NotificationListVO;
import com.gzzhsl.pcms.vo.NotificationVO;
import com.gzzhsl.pcms.vo.OverallNotificationVO;
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
@RequestMapping("/notification")
@Slf4j
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    @GetMapping("/tonotification")
    public String tonotification() {
        return "notification";
    }

    @GetMapping("/getoverallnotification")
    @ResponseBody
    public ResultVO getOverallNotification() {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        BaseInfo thisProject = thisUser.getBaseInfo();
        if (thisUser == null || thisUser.getUserId() == null || thisUser.getUserId() == "") {
            log.error("【通知错误】未读取到用户信息");
            throw new SysException(SysEnum.SIGNIN_PARAM_ERROR);
        }
        if (thisProject == null || thisProject.getBaseInfoId() == null || thisProject.getBaseInfoId() == "") {
            log.error("【通知错误】未读取到该账户下的水库信息");
            throw new SysException(SysEnum.ACCOUNT_NO_PROJECT);
        }
        List<Notification> notifications = notificationService.getAllUnchecked(thisProject.getBaseInfoId()); // 总的未检视的通知
        List<Notification> monthlyReportNotifications = new ArrayList<>(); // 月报的提醒
        List<Notification> projectBasicInfoNotifications = new ArrayList<>(); // 项目基础信息的提醒
        List<Notification> projectPreProgressNotifications = new ArrayList<>(); // 项目基础信息的提醒
        List<Notification> contractsNotifications = new ArrayList<>(); // 合同备案的提醒
        /*如果有其他，继续往这儿加*/
        for (Notification notification : notifications) {
            if ("月报".equals(notification.getType())) {
                monthlyReportNotifications.add(notification);
            } else if ("项目基本信息".equals(notification.getType())) {
                projectBasicInfoNotifications.add(notification);
            } else if ("项目前期信息".equals(notification.getType())) {
                projectPreProgressNotifications.add(notification);
            } else if ("合同备案信息".equals(notification.getType())) {
                contractsNotifications.add(notification);
            }/*如果有其他，继续往这儿加*/
        }

        OverallNotificationVO overallNotificationVO = new OverallNotificationVO();
        Map<String, String> article = new HashMap<>();
        overallNotificationVO.setAllUncheckedNum(notifications.size());
        if (monthlyReportNotifications.size() > 0) {
            article.put("月报新消息", TimeUtil.getDatePoor(new Date(), monthlyReportNotifications.get(0).getCreateTime()));
        }
        if (projectBasicInfoNotifications.size() > 0) {
            article.put("项目基础信息新消息", TimeUtil.getDatePoor(new Date(), projectBasicInfoNotifications.get(0).getCreateTime()));
        }
        if (projectPreProgressNotifications.size() > 0) {
            article.put("项目前期信息新消息", TimeUtil.getDatePoor(new Date(), projectPreProgressNotifications.get(0).getCreateTime()));
        }
        if (contractsNotifications.size() > 0) {
            article.put("合同备案信息新消息", TimeUtil.getDatePoor(new Date(), contractsNotifications.get(0).getCreateTime()));
        }/*如果有其他，继续往这儿加*/
        overallNotificationVO.setArticle(article);
        return ResultUtil.success(overallNotificationVO);
    }

    @GetMapping("/changetochecked")
    @ResponseBody
    public ResultVO changeToChecked(String notificationId) {
        Notification notification = notificationService.getById(notificationId);
        notification.setChecked(true);
        notificationService.save(notification);
        return ResultUtil.success();
    }

    @GetMapping("/querynotification")
    @ResponseBody
    public Page<Notification> queryNotification(@RequestParam(required = false, name = "pageSize", defaultValue = "15") Integer pageSize,
                                       @RequestParam(required = false, name = "startIndex") Integer startIndex,
                                       @RequestParam(required = false, name = "pageIndex", defaultValue = "0") Integer pageIndex,
                                       @RequestParam(required = false, name = "type", defaultValue = "") String type){
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        BaseInfo thisProject = thisUser.getBaseInfo();
        if (thisProject == null || thisProject.getBaseInfoId() == null || thisProject.getBaseInfoId() == "") {
            log.error("【通知错误】未读取到该账户下的水库信息");
            throw new SysException(SysEnum.ACCOUNT_NO_PROJECT);
        }
        Integer page = pageIndex;
        Integer size = pageSize;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Page<Notification> notifications = notificationService.findAllByType(pageRequest, thisProject.getBaseInfoId(), type);
        return notifications;
    }
}
