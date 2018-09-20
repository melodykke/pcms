package com.gzzhsl.pcms.util;

import com.gzzhsl.pcms.model.OperationLog;

import java.util.Date;

public class OperationUtil {
    public static OperationLog buildOperationLog(String userId, Date time, String msg) {
        OperationLog operationLog = new OperationLog();
        operationLog.setOperationLogId(UUIDUtils.getUUIDString());
        operationLog.setUserId(userId);
        operationLog.setCreateTime(time);
        operationLog.setMsg(msg);
        return operationLog;
    }
}
