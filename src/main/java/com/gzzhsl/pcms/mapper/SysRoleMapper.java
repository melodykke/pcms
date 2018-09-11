package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.SysRole;
import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(SysRole record);

    SysRole selectByPrimaryKey(String roleId);

    List<SysRole> selectAll();

    int updateByPrimaryKey(SysRole record);
}