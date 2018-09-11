package com.gzzhsl.pcms.model;

import lombok.Data;

@Data
public class SysPermission {
    private String permissionId;

    private Boolean available;

    private String name;

    private Integer parentId;

    private String parentIds;

    private String permission;

    private String resourceType;

    private String url;
}