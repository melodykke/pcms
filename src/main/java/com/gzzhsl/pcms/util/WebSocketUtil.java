package com.gzzhsl.pcms.util;

import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.impl.WebSocket;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketUtil {
    public static void sendWSMsg(UserInfo thisUser, WebSocket webSocket, String title, String msg) {
        if (UserUtil.isChecker(thisUser)) {
            webSocket.sendMsg(title, msg, "/notification/tonotification", thisUser);
        } else if (UserUtil.isReporter(thisUser)){
            if (thisUser.getParent() == null) {
                log.error("【基本信息错误】 项目基础信息提交错误，提交人员无上级审批单位");
                throw new SysException(SysEnum.BASE_INFO_SUBMIT_NO_PARENT_ERROR);
            }
            webSocket.sendMsg(title, msg, "/notification/tonotification", thisUser.getParent());
        }
    }
}
