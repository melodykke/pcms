package com.gzzhsl.pcms.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class UserInfoDTO {
    private String userId;
    @NotBlank(message = "用户名不可以为空！")
    private String username;
    @NotBlank(message = "水库名不可以为空！")
    private String name;
}
