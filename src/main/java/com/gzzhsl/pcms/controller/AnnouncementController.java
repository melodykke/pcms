package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.entity.Announcement;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.AnnouncementService;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.AnnouncementVO;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/announcement")
@Slf4j
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/toannouncement")
    public String toAnnouncement() {
        return "announcement";
    }

    @PostMapping("/post")
    @ResponseBody
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

    @GetMapping("gethotlatests")
    @ResponseBody
    public ResultVO getHotLatests() {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(0, 3, sort);
        Page<Announcement> announcements = announcementService.getHotLatests(pageRequest);
        if (announcements != null && announcements.getContent().size() > 0) {
            return ResultUtil.success(announcements);
        } else {
            return ResultUtil.failed();
        }
    }
}
