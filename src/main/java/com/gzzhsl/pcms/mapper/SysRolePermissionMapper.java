package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.SysRolePermission;
import java.util.List;

public interface SysRolePermissionMapper {
    int insert(SysRolePermission record);

    List<SysRolePermission> selectAll();
}