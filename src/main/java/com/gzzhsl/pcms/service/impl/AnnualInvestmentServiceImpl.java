package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.entity.AnnualInvestment;
import com.gzzhsl.pcms.entity.AnnualInvestmentImg;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.Notification;
import com.gzzhsl.pcms.enums.NotificationTypeEnum;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.AnnualInvestmentImgRepository;
import com.gzzhsl.pcms.repository.AnnualInvestmentRepository;
import com.gzzhsl.pcms.service.AnnualInvestmentService;
import com.gzzhsl.pcms.service.NotificationService;
import com.gzzhsl.pcms.service.OperationLogService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.OperationUtil;
import com.gzzhsl.pcms.util.PathUtil;
import com.gzzhsl.pcms.util.WebSocketUtil;
import com.gzzhsl.pcms.vo.AnnualInvestmentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class AnnualInvestmentServiceImpl implements AnnualInvestmentService {

    @Autowired
    private AnnualInvestmentRepository annualInvestmentRepository;
    @Autowired
    private AnnualInvestmentImgRepository annualInvestmentImgRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private WebSocket webSocket;


    @Override
    public boolean containsSameYear(String year) {
        List<AnnualInvestment> annualInvestments = this.getMyPassedAnnualInvestments();
        List<String> years = annualInvestments.stream().map(e -> e.getYear()).collect(Collectors.toList());
        if (years.contains(year)) {
            return true;
        }
        return false;
    }


    @Override
    public AnnualInvestment save(AnnualInvestmentVO annualInvestmentVO) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        String year = annualInvestmentVO.getYear();
        List<AnnualInvestment> annualInvestments = this.getMyAnnualInvestmentsByYear(year);
        if (annualInvestments.size() > 1) {
            log.error("【年度投资计划】 年度投资计划新增错误，相同年份存在多个副本。year={}", year);
            throw new SysException(SysEnum.ANNUAL_INVESTMENT_APPLY_YEAR_HAS_DUPLICATED_ERROR);
        }
        if (annualInvestments.size() == 1) {
            AnnualInvestment former = annualInvestments.get(0);
            annualInvestmentVO.setAnnualInvestmentId(former.getAnnualInvestmentId());
            annualInvestmentVO.setCreateTime(former.getCreateTime());
            annualInvestmentImgRepository.deleteByAnnualInvestment(former);
        } else if (annualInvestments.size() == 0) {
            annualInvestmentVO.setCreateTime(new Date());
        }
        AnnualInvestment annualInvestment = new AnnualInvestment();
        annualInvestmentVO.setUpdateTime(new Date());
        annualInvestmentVO.setState((byte) 0);
        annualInvestmentVO.setBaseInfo(thisProject);
        annualInvestmentVO.setSubmitter(thisUser.getUsername());
        BeanUtils.copyProperties(annualInvestmentVO, annualInvestment);
        AnnualInvestment annualInvestmentRt = annualInvestmentRepository.save(annualInvestment);
        // 考虑图片的情况
        if (annualInvestmentVO.getRtFileTempPath() != null && annualInvestmentVO.getRtFileTempPath() != "") { // 如果存在图片
            File sourceDestFolder = new File(PathUtil.getFileBasePath(true) + annualInvestmentVO.getRtFileTempPath());
            String targetDest = PathUtil.getFileBasePath(false) + PathUtil.getAnnualInvestmentImagePath(thisProject.getPlantName(), year);
            File[] sourceFiles = sourceDestFolder.listFiles();
            List<AnnualInvestmentImg> annualInvestmentImgs = new ArrayList<>();
            if (sourceFiles.length > 0) {
                for (File sourceFile : sourceFiles) {
                    File targetFile = new File(targetDest + sourceFile.getName());
                    if (!targetFile.exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    String imgRelativePath = PathUtil.getAnnualInvestmentImagePath(thisProject.getPlantName(), year) + sourceFile.getName();
                    sourceFile.renameTo(targetFile);
                    // 把存储到新地址的图片的相对路径拿出来构建合同图片对象。
                    AnnualInvestmentImg annualInvestmentImg = new AnnualInvestmentImg();
                    annualInvestmentImg.setCreateTime(new Date());
                    annualInvestmentImg.setImgAddr(imgRelativePath);
                    annualInvestmentImg.setAnnualInvestment(annualInvestmentRt);
                    annualInvestmentImgs.add(annualInvestmentImg);
                }
            }
            annualInvestmentRt.setAnnualInvestmentImgs(annualInvestmentImgs);
        }
        // 把通知提醒也一并存入数据库
        Notification notification = new Notification();
        notification.setCreateTime(annualInvestmentRt.getUpdateTime());
        notification.setSubmitter(thisUser.getUsername());
        notification.setType(NotificationTypeEnum.ANNUAL_INVESTMENT.getMsg());
        notification.setTypeId(annualInvestmentRt.getAnnualInvestmentId()); // 这里是项目前期信息ID
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        notification.setYearmonth(formatter.format(annualInvestmentRt.getUpdateTime()));
        notification.setChecked(false);
        notification.setBaseInfoId(thisUser.getBaseInfo().getBaseInfoId());
        notification.setUrl("/annualinvestment/toannualinvestmentshow");
        notificationService.save(notification);
        operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                annualInvestmentRt.getUpdateTime(),
                "提交了:"+ annualInvestmentRt.getYear() +"年的年度投资计划信息"));
        // 创建webSocket消息
        WebSocketUtil.sendWSNotificationMsg(thisUser, webSocket, "年度投资计划", "消息待查收");
        return annualInvestmentRt;
    }

    @Override
    public Page<AnnualInvestment> findByState(Pageable pageable, byte state) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        if (thisProject == null) {
            log.error("【年度投资计划】 获取年度投资计划列表错误， 账号无对应的水库项目");
            throw new SysException(SysEnum.ANNUAL_INVESTMENT_NO_PROJECT_ERROR);
        }
        Specification querySpecification = new Specification() {
            List<Predicate> predicates = new ArrayList<>();
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                if (StringUtils.isNotBlank(thisProject.getBaseInfoId())) {
                    predicates.add(cb.equal(root.join("baseInfo").get("baseInfoId").as(String.class), thisProject.getBaseInfoId()));
                }
                predicates.add(cb.equal(root.get("state"), state));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return annualInvestmentRepository.findAll(querySpecification, pageable);
    }



    private List<AnnualInvestment> getMyPassedAnnualInvestments() {  // 获得本用户水库下的所有审批通过的年计划投资
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        List<AnnualInvestment> annualInvestments = null;
        Specification querySpecification = new Specification() {
            List<Predicate> predicates = new ArrayList<>();
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                predicates.add(cb.equal(root.join("baseInfo").get("baseInfoId").as(String.class), thisProject.getBaseInfoId()));
                predicates.add(cb.equal(root.get("state").as(Byte.class), (byte) 1));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        annualInvestments = annualInvestmentRepository.findAll(querySpecification);
        return annualInvestments;
    }

    private List<AnnualInvestment> getMyAnnualInvestmentsByYear(String year) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        Specification querySpecification = new Specification() {
            List<Predicate> predicates = new ArrayList<>();
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                predicates.add(cb.equal(root.join("baseInfo").get("baseInfoId").as(String.class), thisProject.getBaseInfoId()));
                predicates.add(cb.equal(root.get("year").as(String.class), year));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return annualInvestmentRepository.findAll(querySpecification);

    }
}
