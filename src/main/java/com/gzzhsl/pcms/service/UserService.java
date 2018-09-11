package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.vo.UserInfoVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserInfo findByUserId(String uid);
    UserInfo getUserByUsername(String username);
    List<UserInfo> getAllUser();
    UserInfo save(UserInfo userInfo);
    UserInfo findParentByUsername(String username);
    Integer updateUserBaseInfo(BaseInfo baseInfo, String userId);
    Integer updateUserOpenId(String openId, String userId);
    UserInfo findByOpenId(String openId);
    Page<UserInfoVO> findAll(Pageable pageable);
    Integer toggleActivate(UserInfo userInfo);
    Integer modifyPassword(String password, UserInfo userInfo);








    int deleteByPrimaryKey(String userId);

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(String userId);

    List<UserInfo> selectAll();

    int updateByPrimaryKey(UserInfo record);

    UserInfo findByUsername(String username);

    /**
     * 通过用户名或者ID 查找用户及用户的角色信息
     * @param username
     * @param userId
     * @return
     */
    UserInfo findOneWithRolesByUsernameOrId(String username, String userId);

    /**
     * 通过用户名或者ID 查找用户、用户的角色及权限信息
     * @param username
     * @param userId
     * @return
     */
    UserInfo findOneWithRolesAndPrivilegesByUsernameOrId(String username, String userId);
}
