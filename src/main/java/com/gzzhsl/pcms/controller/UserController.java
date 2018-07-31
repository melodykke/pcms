package com.gzzhsl.pcms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzzhsl.pcms.converter.PersonInfo2VO;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.PersonInfo;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.PersonService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.SysRole;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.HttpServletRequestUtil;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.AccountInfoVO;
import com.gzzhsl.pcms.vo.PersonInfoVO;
import com.gzzhsl.pcms.vo.ResultVO;
import com.gzzhsl.pcms.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;

    @GetMapping("/personinfo")
    public String personInfo() {
        return "person_info";
    }


    @GetMapping("/getthisuser")
    @RequiresUser
    @ResponseBody
    public ResultVO getSubject(){
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.findByUserId(userInfo.getUserId());
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(thisUser, userInfoVO);
        PersonInfo thisPerson = thisUser.getPersonInfo();
        if (thisPerson != null) {
            if (thisPerson.getProfileImg() != null || "".equals(thisPerson.getProfileImg())) {
                userInfoVO.setProfileImg(thisPerson.getProfileImg());
            }
            if (thisPerson.getNickName() != null || "".equals(thisPerson.getNickName())) {
                userInfoVO.setNickname(thisPerson.getNickName());
            }
        }
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

    @GetMapping("/getaccountinfo")
    @ResponseBody
    public ResultVO getAccountInfo() {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.findByUserId(userInfo.getUserId());
        AccountInfoVO accountInfoVO = new AccountInfoVO();
        List<SysRole> roles = thisUser.getSysRoleList();
        if (roles != null && roles.size() > 0) {
            for (SysRole sysRole : roles) {
                if ("reporter".equals(sysRole.getRole())) {
                    accountInfoVO.setAccountType("报送账号");
                    break;
                }
                if ("checker".equals(sysRole.getRole())) {
                    accountInfoVO.setAccountType("业主账号");
                    break;
                }
                if ("manager".equals(sysRole.getRole())) {
                    accountInfoVO.setAccountType("业主账号");
                    break;
                }
                if ("admin".equals(sysRole.getRole())) {
                    accountInfoVO.setAccountType("系统管理员");
                    break;
                }
            }
        } else {
            accountInfoVO.setAccountType("未配置");
        }
        accountInfoVO.setUsername(thisUser.getUsername());
        accountInfoVO.setActive(thisUser.getActive());
        return ResultUtil.success(accountInfoVO);
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
    public ResultVO personInfoSubmit(@RequestBody @Valid PersonInfoVO personInfoVO, BindingResult bindingResult){
        if (personInfoVO == null) {
            log.error("【个人信息】 个人信息错误，没有收到有效的personInfoVO , " +
                    "实际personInfoVO = {}", personInfoVO);
            throw new SysException(SysEnum.PERSON_INFO_ERROR);
        }
        if(bindingResult.hasErrors()){
            log.error("【个人信息】参数验证错误， 参数不正确 personInfoVO = {}， 错误：{}", personInfoVO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.findByUserId(userInfo.getUserId());

        PersonInfo thisPerson = thisUser.getPersonInfo();
        if (thisPerson != null) {
            thisPerson.setName(personInfoVO.getName());
            thisPerson.setTel(personInfoVO.getTel());
            thisPerson.setQq(personInfoVO.getQq());
            thisPerson.setEmail(personInfoVO.getEmail());
            thisPerson.setId_num(personInfoVO.getId_num());
            thisPerson.setTitle(personInfoVO.getTitle());
            thisPerson.setAddress(personInfoVO.getAddress());
        } else {
            thisPerson = new PersonInfo();
            BeanUtils.copyProperties(personInfoVO, thisPerson);
            thisPerson.setCreateTime(new Date());
        }
        thisPerson.setUserInfo(thisUser);
        thisPerson.setUpdateTime(new Date());
        PersonInfo personInfoRt = personService.save(thisPerson);
        if (personInfoRt == null || personInfoRt.getPersonId() == null) {
            log.error("【个人信息】 个人信息持久化错误， 参数不正确");
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED);
        }
        return ResultUtil.success();
    }

}
