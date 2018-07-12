package com.gzzhsl.pcms.service.impl;

import com.gzzhsl.pcms.converter.ContractVO2Contract;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.Contract;
import com.gzzhsl.pcms.entity.ContractImg;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.repository.ContractRepository;
import com.gzzhsl.pcms.service.ContractService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.PathUtil;
import com.gzzhsl.pcms.vo.ContractVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
    private UserService userService;

    @Override
    public Contract findById(String id) {
        return contractRepository.findById(id);
    }

    @Override
    public Contract save(ContractVO contractVO) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        // 判断是否是第一个合同（合同内）
        boolean flag = true; // 默认是第一个合同, 若是第一个合同则flag=true 不是flag=false;
        Contract contract = ContractVO2Contract.convert(contractVO);
        List<Contract> allContract = contractRepository.findAll();
        if (allContract.size() > 0) {
            for (Contract contractIndividual : allContract) {
                flag = contractIndividual.getLabel().equals((byte) 1) ? false : true;
            }
        }
        if (flag) {
            contract.setLabel((byte) 1);
        } else {
            contract.setLabel((byte) 2);
        }
        contract.setCreateTime(new Date());
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
        return contractRt;
    }

    @Override
    public Page<Contract> findByState(Pageable pageable, byte state) {
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
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
        return contractRepository.findAll(querySpecification, pageable);
    }
}
