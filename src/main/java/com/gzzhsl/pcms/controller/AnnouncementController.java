package com.gzzhsl.pcms.controller;

import com.github.pagehelper.PageInfo;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.model.Announcement;
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
import org.springframework.ui.Model;
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

    @GetMapping("/getannouncements")
    @ResponseBody
    public PageInfo<Announcement> getAnnouncements(@RequestParam(required = false, name = "rows", defaultValue = "15") Integer pageSize,
                                                   @RequestParam(required = false, name = "startIndex") Integer startIndex,
                                                   @RequestParam(required = false, name = "page", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(required = false, name = "type", defaultValue = "") String type) {
        PageInfo<Announcement> pageInfo = announcementService.findAnnouncementByPage(pageNum, pageSize);
        return pageInfo;
    }
    @GetMapping("gethotlatests")
    @ResponseBody
    public ResultVO getHotLatests() {
        PageInfo<Announcement> pageInfo = announcementService.getHotLatests(1, 3);
        return ResultUtil.success(pageInfo);
    }








    @GetMapping("/toannouncement")
    @RequiresRoles(value = "manager")
    public String toAnnouncement(String announcementId, Model model) {
        if (announcementId != null || !"".equals(announcementId)) {
            model.addAttribute("announcementId", announcementId);
        }
        return "announcement";
    }
    @GetMapping("/isedit")
    @ResponseBody
    public ResultVO isEdit(String announcementId) {
        if (announcementId != null && announcementId != "") {
            //Announcement announcement = announcementService.getById(announcementId);
            announcementId = null;
            //return ResultUtil.success(announcement);
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
        //Announcement announcement = announcementService.save(announcementVO);
        Announcement announcement= null;
        if (announcement != null) {
            return ResultUtil.success();
        }
        return ResultUtil.failed();
    }

    @GetMapping("toannouncementshow")
    public String toAnnouncementShow(String announcementId, Model model) {
        model.addAttribute("announcementId", announcementId);
        return "announcement_show";
    }
    @GetMapping("getannouncement")
    @ResponseBody
    public ResultVO getAnnouncement(String announcementId) {
        AnnouncementVO announcementVO = announcementService.getAnnouncementById(announcementId);
        return ResultUtil.success(announcementVO);
    }

    @GetMapping("/getallannouncement")
    @ResponseBody
    public ResultVO getAllAnnouncement() {
        return ResultUtil.success(announcementService.getAll());
    }




    @GetMapping("getnormallatests")
    @ResponseBody
    public ResultVO getNormalLatests() {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = new PageRequest(0, 3, sort);
      /*  Page<Announcement> announcements = announcementService.getNormalLatests(pageRequest);
        if (announcements != null && announcements.getContent().size() > 0) {
            return ResultUtil.success(announcements);
        } else {
            return ResultUtil.failed();
        }*/
      return  null;
    }




}
