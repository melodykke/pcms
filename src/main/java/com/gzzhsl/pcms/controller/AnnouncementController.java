package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.Announcement;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.AnnouncementService;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.AnnouncementVO;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/announcement")
@Slf4j
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    private String announcementId;

    @GetMapping("/toannouncement")
    @RequiresRoles(value = "manager")
    public synchronized String toAnnouncement(String announcementId) {
        this.announcementId = announcementId;
        return "announcement";
    }
    @GetMapping("/isedit")
    @ResponseBody
    public synchronized ResultVO isEdit() {
        if (announcementId != null && announcementId != "") {
            Announcement announcement = announcementService.getById(announcementId);
            announcementId = null;
            return ResultUtil.success(announcement);
        }
        return null;
    }
    @GetMapping("/toannouncementmanagement")
    @RequiresRoles(value = "manager")
    public String toAnnouncementManagement() {
        return "announcement_management";
    }
    @PostMapping("/post")
    @ResponseBody
    @RequiresRoles(value = "manager")
    public ResultVO post(@RequestBody @Valid AnnouncementVO announcementVO, BindingResult bindingResult) {
        if (announcementVO == null) {
            log.error("【公告错误】 没有收到有效的announcementVO， announcementVO={}", announcementVO);
            throw new SysException(SysEnum.ANNOUNCEMENT_VO_ERROR);
        }
        if(bindingResult.hasErrors()){
            log.error("【公告错误】参数验证错误，参数不正确 announcementVO = {}， 错误：{}", announcementVO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        Announcement announcement = announcementService.save(announcementVO);
        if (announcement != null) {
            return ResultUtil.success();
        }
        return ResultUtil.failed();
    }

    @GetMapping("/getallannouncement")
    @ResponseBody
    public ResultVO getAllAnnouncement() {
        return ResultUtil.success(announcementService.getAll());
    }

    @GetMapping("/getannouncements")
    @ResponseBody
    public Page<Announcement> getAnnouncements(@RequestParam(required = false, name = "rows", defaultValue = "15") Integer pageSize,
                                                 @RequestParam(required = false, name = "startIndex") Integer startIndex,
                                                 @RequestParam(required = false, name = "page", defaultValue = "1") Integer pageIndex,
                                                 @RequestParam(required = false, name = "type", defaultValue = "") String type) {
        Integer page = pageIndex-1;
        Integer size = pageSize;
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Page<Announcement> announcements = announcementService.findAll(pageRequest);
        return announcements;
    }


    @GetMapping("getnormallatests")
    @ResponseBody
    public ResultVO getNormalLatests() {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = new PageRequest(0, 3, sort);
        Page<Announcement> announcements = announcementService.getNormalLatests(pageRequest);
        if (announcements != null && announcements.getContent().size() > 0) {
            return ResultUtil.success(announcements);
        } else {
            return ResultUtil.failed();
        }
    }

    @GetMapping("gethotlatests")
    @ResponseBody
    public ResultVO getHotLatests() {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = new PageRequest(0, 3, sort);
        Page<Announcement> announcements = announcementService.getHotLatests(pageRequest);
        if (announcements != null && announcements.getContent().size() > 0) {
            return ResultUtil.success(announcements);
        } else {
            return ResultUtil.failed();
        }
    }
}
