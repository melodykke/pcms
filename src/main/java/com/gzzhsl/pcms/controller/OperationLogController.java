package com.gzzhsl.pcms.controller;

import com.github.pagehelper.PageInfo;
import com.gzzhsl.pcms.model.OperationLog;
import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.service.OperationLogService;
import com.gzzhsl.pcms.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/operationlog")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private UserService userService;

    @GetMapping("/tooperationlog")
    public String toOperationLog(){
        return "operation_log";
    }

    @GetMapping("/querylog")
    @ResponseBody
    public PageInfo<OperationLog> queryLog(@RequestParam(required = false, name = "pageSize", defaultValue = "15") Integer pageSize,
                                       @RequestParam(required = false, name = "startIndex") Integer startIndex,
                                       @RequestParam(required = false, name = "pageIndex", defaultValue = "1") Integer pageNum,
                                       @RequestParam(required = false, name = "searchParam", defaultValue = "") String searchParam){
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.findOneWithRolesAndPrivilegesByUsernameOrId(userInfo.getUsername(), null);
        PageInfo pageInfo = operationLogService.findByConditions(pageNum, pageSize, thisUser.getUserId(), searchParam);
        return pageInfo;
    }


}
