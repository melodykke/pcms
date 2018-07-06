package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.OperationLog;
import com.gzzhsl.pcms.service.OperationLogService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.ResultVO;
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

    @GetMapping("/querylog")
    @ResponseBody
    public Page<OperationLog> queryLog(@RequestParam(required = false, name = "pageSize", defaultValue = "15") Integer pageSize,
                                       @RequestParam(required = false, name = "startIndex") Integer startIndex,
                                       @RequestParam(required = false, name = "pageIndex", defaultValue = "1") Integer pageIndex){
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        Integer page = pageIndex;
        Integer size = pageSize;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Page<OperationLog> operationLogs = operationLogService.listAll(pageRequest, thisUser.getUserId());
        return operationLogs;
    }
}
