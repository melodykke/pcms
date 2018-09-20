package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.UserInfoDTO2;
import com.gzzhsl.pcms.dto.UserInfoDTO;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.mapper.SysRoleMapper;
import com.gzzhsl.pcms.mapper.SysUserRoleMapper;
import com.gzzhsl.pcms.model.SysRole;
import com.gzzhsl.pcms.model.SysUserRole;
import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.repository.RoleRepository;
import com.gzzhsl.pcms.service.AccountService;
import com.gzzhsl.pcms.service.SysService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.util.AccountVO2UserInfo;
import com.gzzhsl.pcms.util.UserUtil;
import com.gzzhsl.pcms.vo.AccountPasswordVO;
import com.gzzhsl.pcms.vo.AccountVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Autowired
    private UserService userService;
    @Autowired
    private SysService sysService;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Boolean modifyPassword(AccountPasswordVO accountPasswordVO) {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.findOneWithRolesAndPrivilegesByUsernameOrId(null, userInfo.getUserId());
        // 1、判断原密码是否与本账号密码匹配
        String encriNewPassword = UserUtil.getEncriPwd(accountPasswordVO.getNewPassword());
        if (encriNewPassword.equals(thisUser.getPassword())) {
            log.error("【账户错误】参数验证错误， 新密码和原密码相同 accountPasswordVO = {}", accountPasswordVO);
            throw new SysException(SysEnum.ACCOUNT_PASSWORD_INCONSISTENCY);
        }
        // 2、修改密码
        userService.modifyPassword(encriNewPassword, thisUser);
        return true;
    }

    @Override
    public Boolean createSubAccount(AccountVO accountVO, UserInfo thisUser) {
        UserInfo otherUser = userService.getUserByUsername(accountVO.getUsername());
        // 判断子账号名是否重复
        if (otherUser != null) {
            log.error("【账户错误】账户名错误，已存在相同的账号 accountVO = {}, 相同账号为 otherUser: {}", accountVO, otherUser);
            throw new SysException(SysEnum.ACCOUNT_DUPLICATED);
        }
        UserInfo subUser = AccountVO2UserInfo.convert(accountVO, thisUser);
        // 如果本账号有水库概况了，就把项目概况加给子账号
        String baseInfoId = thisUser.getBaseInfoId();
        if (baseInfoId != null) {
            subUser.setBaseInfoId(baseInfoId);
        }
        subUser.setParentId(thisUser.getParentId());
        List<SysRole> sysRoles = new ArrayList<>();
        SysRole sysRole = sysRoleMapper.findByRole("reporter");
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(thisUser.getUserId());
        sysUserRole.setRoleId(sysRole.getRoleId());
        int intRt = sysUserRoleMapper.insert(sysUserRole);
        if (intRt != 1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public UserInfo createUserAccount(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = UserInfoDTO2.convert(userInfoDTO);
        if (userInfoDTO.getUserId() == null || "".equals(userInfoDTO.getUserId())) { // 新增
            userInfo.setCreateTime(new Date());
            userInfo.setUpdateTime(new Date());
            List<SysRole> sysRoles = new ArrayList<>();
            SysRole sysRoleDefault = sysRoleMapper.findByRole("checker");
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userInfo.getUserId());
            sysUserRole.setRoleId(sysRoleDefault.getRoleId());
            sysUserRoleMapper.insert(sysUserRole);
        } else { // 修改
            UserInfo originalUserInfo = userService.findOneWithRolesAndPrivilegesByUsernameOrId(null, userInfoDTO.getUserId());
            userInfo.setUpdateTime(new Date());
            userInfo.setBaseInfoId(originalUserInfo.getBaseInfoId());
            userInfo.setParentId(originalUserInfo.getParentId());
        }
        UserInfo userInfoRt = userService.save(userInfo);
        if (userInfoRt != null && userInfoRt.getUserId() != null) {
            return userInfoRt;
        } else {
            return null;
        }
    }
}
