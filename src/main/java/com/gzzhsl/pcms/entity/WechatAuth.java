package com.gzzhsl.pcms.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 微信登录实体类
 * 
 * @author xiangze
 *
 */
@Entity
@Getter
@Setter
public class WechatAuth {
	// 主键ID
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String wechatAuthId;
	// 微信获取用户信息的凭证，对于某个公众号具有唯一性
	private String openId;
	// 创建时间
	private Date createTime;
	// 用户信息
    @OneToOne(mappedBy = "wechatAuth", cascade = CascadeType.ALL)
	private PersonInfo personInfo;

}
