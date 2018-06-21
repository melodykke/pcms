package com.gzzhsl.pcms.repository;

import com.gzzhsl.pcms.shiro.bean.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<SysRole, String> {
    SysRole findByRoleId(String roleId);
    SysRole findByRole(String role);
}
