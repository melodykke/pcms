package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.OperationLog;
import com.gzzhsl.pcms.service.OperationLogService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.ResultVO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/operationlog")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/tooperationlog")
    public String toOperationLog(){
        return "/operation_log";
    }

    @GetMapping("/getlog")
    @ResponseBody
    public List<OperationLog> getLog(){
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        List<OperationLog> operationLogs = operationLogService.getOperationLogsByUserId(thisUser.getUserId());
        return operationLogs;
    }
}
