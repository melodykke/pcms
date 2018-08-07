package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.Tender2VO;
import com.gzzhsl.pcms.converter.TenderImg2VO;
import com.gzzhsl.pcms.entity.*;
import com.gzzhsl.pcms.enums.NotificationTypeEnum;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.TenderImgRepository;
import com.gzzhsl.pcms.repository.TenderRepository;
import com.gzzhsl.pcms.service.*;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.FeedbackUtil;
import com.gzzhsl.pcms.util.OperationUtil;
import com.gzzhsl.pcms.util.PathUtil;
import com.gzzhsl.pcms.util.WebSocketUtil;
import com.gzzhsl.pcms.vo.TenderImgVO;
import com.gzzhsl.pcms.vo.TenderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
public class TenderServiceImpl implements TenderService {
    @Autowired
    private TenderRepository tenderRepository;
    @Autowired
    private TenderImgRepository tenderImgRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private WebSocket webSocket;

    @Override
    public Tender save(TenderVO tenderVO) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        Tender tender = new Tender();
        tenderVO.setCreateTime(new Date());
        tenderVO.setUpdateTime(new Date());
        tenderVO.setState((byte) 0);
        tenderVO.setBaseInfo(thisProject);
        tenderVO.setSubmitter(thisUser.getUsername());
        BeanUtils.copyProperties(tenderVO, tender);
        Tender tenderRt = tenderRepository.save(tender);
        if (tenderVO.getTenderId() != null) {
            Tender tenderItem = tenderRepository.findOne(tenderVO.getTenderId());
            tenderVO.setCreateTime(tenderItem.getCreateTime());
            // 如果存在记录 就把该记录下的所有文档全部删除
            tenderImgRepository.deleteByTender(tenderRt);
        }
        // 考虑图片的情况
        if (tenderVO.getRtFileTempPath() != null && tenderVO.getRtFileTempPath() != "") { // 如果存在图片
            File sourceDestFolder = new File(PathUtil.getFileBasePath(true) + tenderVO.getRtFileTempPath());
            String targetDest = PathUtil.getFileBasePath(false) + PathUtil.getTenderImagePath(thisProject.getPlantName());
            File[] sourceFiles = sourceDestFolder.listFiles();
            List<TenderImg> tenderImgs = new ArrayList<>();
            if (sourceFiles.length > 0) {
                for (File sourceFile : sourceFiles) {
                    File targetFile = new File(targetDest + sourceFile.getName());
                    if (!targetFile.exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    String imgRelativePath = PathUtil.getTenderImagePath(thisProject.getPlantName()) + sourceFile.getName();
                    sourceFile.renameTo(targetFile);
                    // 把存储到新地址的图片的相对路径拿出来构建合同图片对象。
                    TenderImg tenderImg = new TenderImg();
                    tenderImg.setCreateTime(new Date());
                    tenderImg.setImgAddr(imgRelativePath);
                    tenderImg.setTender(tenderRt);
                    tenderImgs.add(tenderImg);
                }
            }
            tenderRt.setTenderImgs(tenderImgs);
        }
        // 把通知提醒也一并存入数据库
        Notification notification = new Notification();
        notification.setCreateTime(tenderRt.getUpdateTime());
        notification.setSubmitter(thisUser.getUsername());
        notification.setType(NotificationTypeEnum.TENDER.getMsg());
        notification.setTypeId(tenderRt.getTenderId()); // 这里是项目前期信息ID
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        notification.setYearmonth(formatter.format(tenderRt.getUpdateTime()));
        notification.setChecked(false);
        notification.setBaseInfoId(thisUser.getBaseInfo().getBaseInfoId());
        notification.setUrl("/tender/totendershow");
        notificationService.save(notification);
        operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                tenderRt.getUpdateTime(),
                "提交了ID:"+ tenderRt.getTenderId() +"的招标项目备案"));
        // 创建webSocket消息
        WebSocketUtil.sendWSNotificationMsg(thisUser, webSocket, "招标项目备案", "消息待查收");
        return tenderRt;
    }

    @Override
    public Page<TenderVO> findByState(Pageable pageable, byte state) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        if (thisProject == null) {
            log.error("【招标备案】 获取年度招标备案列表错误， 账号无对应的水库项目");
            throw new SysException(SysEnum.TENDER_NO_PROJECT_ERROR);
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
        Page<Tender> tenderPage = tenderRepository.findAll(querySpecification, pageable);
        List<Tender> tenders = tenderPage.getContent();
        List<TenderVO> tenderVOs = tenders.stream().map(e -> Tender2VO.convert(e)).collect(Collectors.toList());
        Page<TenderVO> tenderVOpage = new PageImpl<TenderVO>(tenderVOs, pageable, tenderPage.getTotalElements());
        return tenderVOpage;
    }

    @Override
    public TenderVO getById(String id) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        //获取该用户所有年度投融资计划
        List<Tender> tenders = tenderRepository.findAllByBaseInfo(thisProject);
        List<String> tenderIds = tenders.stream().map(e -> e.getTenderId()).collect(Collectors.toList());
        //判断查询ID是否在其中   //如果不在抛异常（不能查询别人的） 如果在返回结果
        if (!tenderIds.contains(id)) {
            log.error("【招标管理】 不能查询不属于自己的招标项目");
            throw new SysException(SysEnum.TENDER_QUERY_OTHERS_ERROR);
        }
        Tender tender = tenderRepository.findOne(id); //下面转化为VO
        TenderVO tenderVO = new TenderVO();
        BeanUtils.copyProperties(tender, tenderVO);
        List<TenderImg> tenderImgs = tender.getTenderImgs();
        List<TenderImgVO> tenderImgVOs = tenderImgs.stream().map(e -> TenderImg2VO.convert(e)).collect(Collectors.toList());
        tenderVO.setTenderImgVOs(tenderImgVOs);
        return tenderVO;
    }

    @Override
    public Tender findById(String id) {
        return tenderRepository.findByTenderId(id);
    }


    @Override
    public Feedback approveTender(UserInfo thisUser, boolean switchState, String checkInfo, Tender tender) {
        Feedback feedbackRt = null;
        if (switchState == false) {
            tender.setState((byte) 1); // 审批通过
            Tender thisTenderRt = tenderRepository.save(tender);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getBaseInfo().getBaseInfoId(), thisUser.getUsername(),"招标备案信息", thisTenderRt.getTenderId(), new Date(),
                    "审批通过", (byte) 1, "tender/totendershow");
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批通过了ID为"+feedbackRt.getTargetId()+"的招标备案信息"));
        } else {
            tender.setState((byte) -1); // 审批未通过
            Tender thisTenderRt = tenderRepository.save(tender);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getBaseInfo().getBaseInfoId(), thisUser.getUsername(), "招标备案信息",thisTenderRt.getTenderId(), new Date(),
                    "审批未通过：", (byte) -1, "tender/totendershow");
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批未通过ID为"+feedbackRt.getTargetId()+"的招标备案信息"));
        }
        // 创建webSocket消息
        WebSocketUtil.sendWSFeedbackMsg(thisUser, webSocket, "招标备案", "新的招标备案审批消息");
        return feedbackRt;
    }
}
