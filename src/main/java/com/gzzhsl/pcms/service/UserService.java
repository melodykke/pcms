package com.gzzhsl.pcms.service;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.model.PersonInfo;
import com.gzzhsl.pcms.model.UserInfo;
import com.gzzhsl.pcms.vo.UserInfoVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

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

    /**
     * 通过用户ID查找用户个人信息
     * @param userId
     * @return
     */
    PersonInfo findPersonInfoByUserId(String userId);

    /**
     * 找到当前账号的上级用户
     * @param thisUser
     * @return
     */
    UserInfo findParent(UserInfo thisUser);

    /**
     * 找到当前账号的子账号
     * @param thisUser
     * @return
     */
    List<UserInfo> findChildren(UserInfo thisUser);

    /**
     * 更新用户集合里用户的baseInfoId值
     * @param userInfos
     * @param baseInfoId
     * @return
     */
    Integer batchUpdateBaseInfoId(List<UserInfo> userInfos, String baseInfoId);















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


}
