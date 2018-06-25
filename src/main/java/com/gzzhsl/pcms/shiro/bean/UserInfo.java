package com.gzzhsl.pcms.shiro.bean;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gzzhsl.pcms.entity.PersonInfo;
import com.gzzhsl.pcms.entity.Project;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@DynamicUpdate
public class UserInfo {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String userId;
    private String username;
    private String password;
    private String name;
    private String salt;
    private Byte active; //0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
    @JsonManagedReference
    @OneToOne(mappedBy = "userInfo")
    private PersonInfo personInfo;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private UserInfo parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserInfo> children = new ArrayList<UserInfo>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns ={@JoinColumn(name = "roleId") })
    private List<SysRole> sysRoleList = new ArrayList<>();
    private Date createTime;
    private Date updateTime;

    //为了密码更安全，使用username + salt
    public String fetchUsernameAndSalt() {
        return this.username + this.salt;
    }
}
