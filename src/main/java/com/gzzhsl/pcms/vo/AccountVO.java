package com.gzzhsl.pcms.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountVO {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String rePassword;
}
