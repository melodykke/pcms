package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.ContractVO2Contract;
import com.gzzhsl.pcms.entity.*;
import com.gzzhsl.pcms.enums.NotificationTypeEnum;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.ContractImgRepository;
import com.gzzhsl.pcms.repository.ContractRepository;
import com.gzzhsl.pcms.service.*;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.FeedbackUtil;
import com.gzzhsl.pcms.util.OperationUtil;
import com.gzzhsl.pcms.util.PathUtil;
import com.gzzhsl.pcms.util.WebSocketUtil;
import com.gzzhsl.pcms.vo.ContractVO;
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

@Service
@Slf4j
@Transactional
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private ContractImgRepository contractImgRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private NotificationService notificationService;

    @Override
    public Contract findById(String id) {
        // return contractRepository.findById(id);
        return null;
    }

    @Override
    public Boolean hasInnerContract() { // 点击新增的前提条件
      /*  UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        List<Contract> contracts = thisProject.getContracts();
        for (Contract contract : contracts) {
            if (contract.getLabel().equals((byte) 1) && contract.getState().equals((byte) 1)) { // 有合同内，并审批通过
                return true;
            }
        }*/
        return false;
    }

    @Override
    public Contract save(ContractVO contractVO) {
      /*  UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        Contract innerContract = null;
        // 判断是否是第一个合同（合同内）
        boolean flag = true; // 默认是第一个合同, 若是第一个合同则flag=true 不是flag=false;
        Contract contract = ContractVO2Contract.convert(contractVO);
        List<Contract> allContract = contractRepository.findAll();
        if (allContract.size() > 0) {
            for (Contract contractIndividual : allContract) {
                if (contractIndividual.getLabel().equals((byte) 1)) {
                    if (!contractIndividual.getState().equals((byte) 1)) {
                        innerContract = contractIndividual;
                        flag = true;
                        break;
                    } else {
                        flag = false;
                        break;
                    }
                } else {
                    flag = true;
                }
            }
        }
        if (flag) {
            contract.setLabel((byte) 1); // 内
        } else {
            contract.setLabel((byte) 2); // 外
        }

        if (innerContract != null) {
            contract.setId(innerContract.getId());
            // 把之前的图片删掉
            contractImgRepository.deleteByContract(innerContract);
        }
        contract.setBaseInfo(thisProject);
        Contract contractRt = contractRepository.save(contract);
        if (contractVO.getRtFileTempPath() != null ) {
            File sourceDestFolder = new File(PathUtil.getFileBasePath(true) + contractVO.getRtFileTempPath());
            String targetDest = PathUtil.getFileBasePath(false) + PathUtil.getContractImagePath(thisProject.getPlantName());
            File[] sourceFiles = sourceDestFolder.listFiles();
            List<ContractImg> contractImgs = new ArrayList<>();
            if (sourceFiles.length > 0) {
                for (File sourceFile : sourceFiles) {
                    File targetFile = new File(targetDest + sourceFile.getName());
                    if (!targetFile.exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    String imgRelativePath = PathUtil.getContractImagePath(thisProject.getPlantName()) + sourceFile.getName();
                    sourceFile.renameTo(targetFile);
                    // 把存储到新地址的图片的相对路径拿出来构建合同图片对象。
                    ContractImg contractImg = new ContractImg();
                    contractImg.setCreateTime(new Date());
                    contractImg.setImgAddr(imgRelativePath);
                    contractImg.setContract(contractRt);
                    contractImgs.add(contractImg);
                }
            }
            contractRt.setContractImgs(contractImgs);
        }
        // 把通知提醒也一并存入数据库
        Notification notification = new Notification();
        notification.setCreateTime(contractRt.getUpdateTime());
        notification.setSubmitter(thisUser.getUsername());
        notification.setType(NotificationTypeEnum.PROJECT_CONTRACT.getMsg());
        notification.setTypeId(contractRt.getId()); // 这里是项目前期信息ID
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        notification.setYearmonth(formatter.format(contractRt.getUpdateTime()));
        notification.setChecked(false);
        notification.setBaseInfoId(thisUser.getBaseInfo().getBaseInfoId());
        notification.setUrl("/contract/tocontract");
        notificationService.save(notification);
        operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(),
                contractRt.getUpdateTime(),
                "提交了ID:"+ contractRt.getId() +"的合同备案信息"));
        // 创建webSocket消息
        WebSocketUtil.sendWSNotificationMsg(thisUser, webSocket, "合同备案信息", "新的合同备案消息待查收");*/
        return null;
    }

    @Override
    public Page<Contract> findByState(Pageable pageable, byte state) {
      /*  UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        if (thisProject == null) {
            log.error("【合同错误】 获取合同列表错误， 账号无对应的水库项目");
            throw new SysException(SysEnum.CONTRACT_NO_PROJECT_ERROR);
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
        return contractRepository.findAll(querySpecification, pageable);*/
      return null;
    }

    @Override
    public Feedback approveContract(UserInfo thisUser, Boolean switchState, String checkinfo, Contract thisContract) {
        Feedback feedbackRt = null;
        if (switchState == false) {
            thisContract.setState((byte) 1); // 审批通过
            Contract thisContractRt = contractRepository.save(thisContract);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getBaseInfo().getBaseInfoId(), thisUser.getUsername(),"合同备案信息", thisContract.getId(), new Date(),
                    "审批通过", (byte) 1, "contract/tocontract");
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批通过了ID为"+feedbackRt.getTargetId()+"的合同备案信息"));
        } else {
            thisContract.setState((byte) -1); // 审批未通过
            Contract thisContractRt = contractRepository.save(thisContract);
            Feedback feedback = FeedbackUtil.buildFeedback(thisUser.getBaseInfo().getBaseInfoId(), thisUser.getUsername(), "合同备案信息",thisContract.getId(), new Date(),
                    "审批未通过：" + checkinfo, (byte) -1, "preprogress/topreprogress");
            feedbackRt = feedbackService.save(feedback);
            operationLogService.save(OperationUtil.buildOperationLog(thisUser.getUserId(), feedbackRt.getCreateTime(), "审批未通过ID为"+feedbackRt.getTargetId()+"的合同备案信息"));
        }
        // 创建webSocket消息
        WebSocketUtil.sendWSFeedbackMsg(thisUser, webSocket, "合同备案", "新的合同备案审批消息");
        return feedbackRt;
    }
}
