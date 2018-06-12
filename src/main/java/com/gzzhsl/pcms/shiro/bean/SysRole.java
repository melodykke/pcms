package com.gzzhsl.pcms.shiro.bean;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicUpdate
public class SysRole {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String roleId;
    private String role;// 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description;
    private Boolean available = Boolean.FALSE;
    @ManyToMany
    @JoinTable(name = "sys_user_role", joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="userId")})
    private List<UserInfo> userInfoList;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="sys_role_permission",joinColumns={@JoinColumn(name="roleId")},inverseJoinColumns={@JoinColumn(name="permissionId")})
    private List<SysPermission> sysPermissionList;
}
