package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.SysUserRole;
import java.util.List;

public interface SysUserRoleMapper {
    int insert(SysUserRole record);

    List<SysUserRole> selectAll();
}