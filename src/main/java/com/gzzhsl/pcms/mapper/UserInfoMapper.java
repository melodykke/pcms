package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {
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
    UserInfo findOneWithRolesByUsernameOrId(@Param("username") String username, @Param("userId") String userId);

    /**
     * 通过用户名或者ID 查找用户、用户的角色及权限信息
     * @param username
     * @param userId
     * @return
     */
    UserInfo findOneWithRolesAndPrivilegesByUsernameOrId(@Param("username") String username, @Param("userId") String userId);

    /**
     * 根据parentId查找父账号
     * @param parentId
     * @return
     */
    UserInfo findParentByParentId(String parentId);

    /**
     * 根据userId找到当前账号的子账号
     * @param userId
     * @return
     */
    List<UserInfo> findChildrenByUserId(String userId);

    /**
     * 更新用户集合里用户的baseInfoId值
     * @param userInfos
     * @param baseInfoId
     * @return
     */
    Integer batchUpdateBaseInfoId(@Param("userInfos") List<UserInfo> userInfos, @Param("baseInfoId") String baseInfoId);
}