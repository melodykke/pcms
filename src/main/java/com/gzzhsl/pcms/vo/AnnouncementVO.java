package com.gzzhsl.pcms.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AnnouncementVO {
    private String announcementId;
    @NotBlank(message = "公告类型不能为空！")
    private String type;
    private Boolean hot;
    @NotBlank(message = "公告标题不能为空！")
    private String title;
    private String keyword;
    @NotBlank(message = "公告内容不能为空！")
    private String content;

    private Date createTime;
    private Date updateTime;
}
