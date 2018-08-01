package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.ProjectStatus;
import com.gzzhsl.pcms.entity.TimeLineItem;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.ProjectStatusRepository;
import com.gzzhsl.pcms.service.ProjectStatusService;
import com.gzzhsl.pcms.service.TimeLineItemService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ProjectStatusServiceImpl implements ProjectStatusService {

    @Autowired
    private ProjectStatusRepository projectStatusRepository;
    @Autowired
    private TimeLineItemService timeLineItemService;
    @Autowired
    private UserService userService;

    @Override
    public ProjectStatus save(ProjectStatus projectStatus) {
        return projectStatusRepository.save(projectStatus);
    }

    @Override
    public ResultVO updateProjectStatus(int id) {
        try {
            UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
            if (id > 8 || id < 0) {
                log.error("【项目状态】 项目状态错误，输入的id不正确！ id={}", id);
                throw new SysException(9999, "项目状态错误，输入的id不正确！");
            }
            TimeLineItem timeLineItem = timeLineItemService.getById(id + 1);
            ProjectStatus projectStatus = new ProjectStatus();
            projectStatus.setBaseInfo(thisProject);
            projectStatus.setCreateTime(new Date());
            projectStatus.setTimeLineItem(timeLineItem);
            projectStatusRepository.save(projectStatus);
            return ResultUtil.success(timeLineItem);
        } catch (Exception e) {
            log.error("【项目状态】 项目状态错误，未知错误！");
            throw new SysException(9998, "项目状态错误，未知错误！");
        }
    }

    @Override
    public List<ProjectStatus> getProjectStatus() {
        try {
            UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
            List<ProjectStatus> projectStatuses = null;
            Specification querySpecification = new Specification() {
                @Override
                public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                    return cb.equal(root.join("baseInfo").get("baseInfoId"), thisProject.getBaseInfoId());
                }
            };
            Sort sort = new Sort(Sort.Direction.ASC, "createTime");

            return projectStatuses = projectStatusRepository.findAll(querySpecification, sort);
        } catch (Exception e) {
            return null;
        }
    }


}
