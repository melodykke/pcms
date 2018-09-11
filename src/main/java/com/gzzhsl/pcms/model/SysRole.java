package com.gzzhsl.pcms.model;

import lombok.Data;

import java.util.List;

@Data
public class SysRole {
    private String roleId;

    private Boolean available;

    private String description;

    private String role;

    private List<SysPermission> permissions;
}