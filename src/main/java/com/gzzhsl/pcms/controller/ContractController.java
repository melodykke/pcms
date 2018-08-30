package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.converter.Contract2VO;
import com.gzzhsl.pcms.converter.ContractImg2VO;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.Contract;
import com.gzzhsl.pcms.entity.Feedback;
import com.gzzhsl.pcms.entity.PreProgress;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.ContractService;
import com.gzzhsl.pcms.service.UserService;
import com.gzzhsl.pcms.shiro.bean.UserInfo;
import com.gzzhsl.pcms.util.FileUtil;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.ContractImgVO;
import com.gzzhsl.pcms.vo.ContractVO;
import com.gzzhsl.pcms.vo.ResultVO;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contract")
@Slf4j
public class ContractController {
    @Autowired
    private ContractService contractService;
    @Autowired
    private UserService userService;

    @GetMapping("/tocontract")
    public String toContract() {
        return "contract_show";
    }

    @GetMapping("/hascontract")
    @ResponseBody
    public ResultVO hasContract() {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        BaseInfo thisProject = thisUser.getBaseInfo();
        List<Contract> contracts = thisProject.getContracts();
        if (contracts.size() > 0) {
            return ResultUtil.success();
        }
        return ResultUtil.failed();
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO save(@Valid @RequestBody ContractVO contractVO, BindingResult bindingResult) {
        System.out.println(contractVO);
        if (contractVO == null) {
            log.error("【合同错误】 contractVO , " +
                    "contractVO = {}", contractVO);
            throw new SysException(SysEnum.CONTRACT_VO_PARAMS_ERROR);
        }
        if (bindingResult.hasErrors()) {
            log.error("【合同错误】参数验证错误， 参数不正确 contractVO = {}， 错误：{}", contractVO, bindingResult.getFieldError().getDefaultMessage());
            throw new SysException(SysEnum.DATA_SUBMIT_FAILED.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        Contract contractRt = contractService.save(contractVO);
        if (contractRt != null) return ResultUtil.success();
        return ResultUtil.failed();
    }

    @PostMapping("/addfiles")
    @ResponseBody
    public ResultVO saveFiles(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("contract_file");
        if (files == null || files.size() < 1) {
            return ResultUtil.failed();
        }
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        return ResultUtil.success(FileUtil.saveFile(thisUser, files));
    }

    @GetMapping("/getcontract")
    @ResponseBody
    public ResultVO getContract(@RequestParam(required = false, name = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(required = false, name = "startIndex") Integer startIndex,
                                @RequestParam(required = false, name = "pageIndex", defaultValue = "1") Integer pageIndex,
                                @RequestParam(required = false, name = "state", defaultValue = "1") byte state) {
        Integer page = pageIndex;
        Integer size = pageSize;
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, size, sort);
        Page<Contract> contracts = contractService.findByState(pageRequest, state);
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        List<String> roles = thisUser.getSysRoleList().stream().map(e -> e.getRole()).collect(Collectors.toList());
        if (roles.contains("checker")) {
            return ResultUtil.success(SysEnum.DATA_CALLBACK_SUCCESS.getCode(), "checker", contracts);
        } else {
            return ResultUtil.success(SysEnum.DATA_CALLBACK_SUCCESS.getCode(), "数据返回成功", contracts);
        }
    }

    @GetMapping("/getcontractbyid")
    @ResponseBody
    public ResultVO getContractById(String id) {
        Contract contract = contractService.findById(id);
        List<ContractImgVO> contractImgVOList = contract.getContractImgs().stream().map(e -> ContractImg2VO.convert(e)).collect(Collectors.toList());
        ContractVO contractVO = Contract2VO.convert(contract);
        contractVO.setContractImgVOs(contractImgVOList);
        return ResultUtil.success(contractVO);
    }

    @PostMapping("/approvecontract")
    @ResponseBody
    @RequiresRoles(value = {"checker"})
    public ResultVO approveContract(@RequestBody Map<String, Object> params) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfo thisUser = userService.getUserByUsername(username);
        BaseInfo thisProject = thisUser.getBaseInfo();
        List<Contract> contracts = thisProject.getContracts();
        Boolean switchState = (boolean) params.get("switchState"); // true: 按钮未通过 false：按钮通过
        String checkinfo = (String) params.get("checkinfo");
        String contractId = (String) params.get("contractId");
        Contract thisContract = contractService.findById(contractId);
        if (contractId == null || contractId == "") {
            log.error("【合同备案错误】审批合同备案错误 合同备案ID contractId为空");
            throw new SysException(SysEnum.CONTRACT_APPROVAL_ERROR);
        }
        if (thisContract == null) {
            log.error("【合同备案错误】审批查询的合同备案所对应ID无记录");
            throw new SysException(SysEnum.CONTRACT_NO_CORRESPOND_RECORD_ERROR);
        }
        if (contracts.size() > 0) {
            boolean noAffiliatedContract = true;
            for (Contract contract : contracts) {
                if (contractId.equals(contract.getId())) {
                    noAffiliatedContract = false;
                    break;
                }
            }
            if (noAffiliatedContract) {
                log.error("【合同备案错误】不能审批不属于本用户所属工程的合同备案信息");
                throw new SysException(SysEnum.CONTRACT_CHECKED_OTHERS_ERROR);
            }
        }
        if (thisContract.getState() != (byte) 0) {
            log.error("【合同备案错误】当前合同备案信息已经审批过，不能重复审批");
            throw new SysException(SysEnum.CONTRACT_CHECK_CHECKED_ERROR);
        }
        Feedback feedback = contractService.approveContract(thisUser, switchState, checkinfo, thisContract);
        if (feedback.getState().equals((byte) 1)) {
            return ResultUtil.success(feedback);
        } else {
            return ResultUtil.failed(feedback);
        }
    }

    @GetMapping("/addouttercontract")
    @ResponseBody
    public ResultVO addOutterContract() {
        if (contractService.hasInnerContract()) {
            return ResultUtil.success();
        } else {
            return ResultUtil.failed();
        }
    }

}
