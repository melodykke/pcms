package com.gzzhsl.pcms.controller;

import com.gzzhsl.pcms.converter.Contract2VO;
import com.gzzhsl.pcms.converter.ContractImg2VO;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.entity.Contract;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
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
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        BaseInfo thisProject = userService.findByUserId(thisUser.getUserId()).getBaseInfo();
        List<Contract> contracts = thisProject.getContracts();
        if (thisProject.getContracts().size() > 0) {
            for (Contract contract : contracts) {
                if (contract.getLabel().equals((byte) 1)) {
                    return ResultUtil.success();
                }
            }
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
        UserInfo thisUser = (UserInfo) SecurityUtils.getSubject().getPrincipal();
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
        return ResultUtil.success(contracts);
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
}
