package com.gzzhsl.pcms.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountVO {
    @NotNull
    private String oriPassword;
    @NotNull
    private String newPassword;
    @NotNull
    private String reNewPassword;
}
