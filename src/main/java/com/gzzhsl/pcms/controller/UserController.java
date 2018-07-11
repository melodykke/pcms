package com.gzzhsl.pcms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzzhsl.pcms.converter.PersonInfo2VO;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.PersonInfo;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.PersonService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.HttpServletRequestUtil;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.PersonInfoVO;
import com.gzzhsl.pcms.vo.ResultVO;
import com.gzzhsl.pcms.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private PersonService personService;

    @GetMapping("/personinfo")
    public String personInfo() {
        return "person_info";
    }


    @GetMapping("/getthisuser")
    @RequiresUser
    @ResponseBody
    public ResultVO getSubject(){
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(userInfo, userInfoVO);
        return ResultUtil.success(userInfoVO);
    }

    @GetMapping("/getthisproject")
    @RequiresUser
    @ResponseBody
    public ResultVO getThisProject(HttpServletRequest request, HttpServletResponse response){
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = thisUser.getBaseInfo();
        if (thisProject == null || thisProject.getBaseInfoId() == null) {
            return ResultUtil.failed(SysEnum.NO_PROJECT_IN_THISUSER);
        }else {
            request.getSession().setAttribute("thisProject", thisProject);
            return ResultUtil.success();
        }
    }


    @GetMapping("/doesthisuserhaspersoninfo")
    @ResponseBody
    public ResultVO doesThisUserHasPersonInfo(){
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        PersonInfo personInfo = personService.getByUserInfo(thisUser);
        if (personInfo == null) {
            return ResultUtil.failed(SysEnum.BOOLEAN_RESULT_FAIL);
        } else {
            PersonInfoVO personInfoVO = PersonInfo2VO.convert(personInfo);
            return ResultUtil.success(personInfoVO);
        }
    }
    @GetMapping("/haspersoninfo")
    @ResponseBody
    public ResultVO hasPersonInfo(){
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        PersonInfo personInfo = personService.getByUserInfo(thisUser);
        if (personInfo == null) {
            return ResultUtil.failed();
        } else {
            return ResultUtil.success();
        }
    }

    @PostMapping("/personinfosubmit")
    @ResponseBody
    public ResultVO personInfoSubmit(HttpServletRequest request){
        System.out.println("/personinfosubmit called");
        ObjectMapper objectMapper = new ObjectMapper();
        String personInfoStr = HttpServletRequestUtil.getString(request, "personInfoStr");
        try {
            PersonInfo personInfo = objectMapper.readValue(personInfoStr, PersonInfo.class);
            UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            if (userInfo.getPersonInfo() != null) {
                log.error("【个人信息】 重复提交个人信息");
                throw new SysException(SysEnum.PERSON_INFO_DUPLICATED);
            }
            personInfo.setUserInfo(userInfo);
            personInfo.setCreateTime(new Date());
            PersonInfo personInfoRt = personService.save(personInfo);
            if (personInfoRt == null || personInfoRt.getPersonId() == null) {
                log.error("【个人信息】 个人信息持久化错误， 参数不正确");
                throw new SysException(SysEnum.DATA_SUBMIT_FAILED);
            }
        } catch (IOException e) {
            log.error("【个人信息】 个人信息提交错误， 参数不正确");
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED);
        }
        return ResultUtil.success();
    }

}
