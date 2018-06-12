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
public class SysPermission {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String permissionId;
    private String name;
    @Column(columnDefinition="enum('menu', 'button')")
    private String resourceType;//资源类型，[menu|button]
    private String url;//资源路径.
    private String permission;//权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private Integer parentId;//父编号
    private String parentIds;//父编号列表
    private Boolean available = Boolean.FALSE;
    @ManyToMany
    @JoinTable(name="sys_role_permission",joinColumns={@JoinColumn(name="permissionId")},inverseJoinColumns={@JoinColumn(name="roleId")})
    private List<SysRole> sysRoleList;
}
