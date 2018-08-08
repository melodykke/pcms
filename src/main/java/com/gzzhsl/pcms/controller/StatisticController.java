package com.gzzhsl.pcms.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzzhsl.pcms.entity.BaseInfo;
import com.gzzhsl.pcms.service.BaseInfoService;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.BaseInfoManagerIndexVO;
import com.gzzhsl.pcms.vo.ResultVO;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/statistic")
public class StatisticController {
    private static final String ALLBASEINFO = "allBaseInfo";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BaseInfoService baseInfoService;


    // 所有工程总投资 从概况里来
    @GetMapping("/getalltotalinvestment")
    @ResponseBody
    @RequiresRoles("manager")
    public ResultVO getAllTotalInvestment() {
        BigDecimal allTotalInvestment = new BigDecimal(0);
        if (stringRedisTemplate.hasKey(ALLBASEINFO)) {
            List<BaseInfoManagerIndexVO> baseInfoManagerIndexVOs = null;
            ObjectMapper objectMapper = new ObjectMapper();
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, BaseInfoManagerIndexVO.class);
            String jsonString = stringRedisTemplate.opsForValue().get(ALLBASEINFO);
            try {
                baseInfoManagerIndexVOs = objectMapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (baseInfoManagerIndexVOs != null && baseInfoManagerIndexVOs.size() > 0) {
                for (BaseInfoManagerIndexVO baseInfoManagerIndexVO : baseInfoManagerIndexVOs) {
                    allTotalInvestment = allTotalInvestment.add(baseInfoManagerIndexVO.getTotalInvestment());
                }
            }
        } else {
            List<BaseInfo> baseInfoList = baseInfoService.getAllProject();
            for (BaseInfo baseInfo : baseInfoList) {
                allTotalInvestment = allTotalInvestment.add(baseInfo.getTotalInvestment());
            }
        }
        return ResultUtil.success(allTotalInvestment);
    }




}
