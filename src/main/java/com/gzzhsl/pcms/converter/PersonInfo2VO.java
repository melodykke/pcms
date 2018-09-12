package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.model.PersonInfo;
import com.gzzhsl.pcms.vo.PersonInfoVO;
import org.springframework.beans.BeanUtils;

public class PersonInfo2VO {
    public static PersonInfoVO convert(PersonInfo personInfo) {
        PersonInfoVO personInfoVO = new PersonInfoVO();
        String iDNum = personInfo.getIdNum();
        if (iDNum != null || "".equals(iDNum)) {
            // 隐藏身份证部分位置数字
            StringBuilder sb = new StringBuilder(personInfo.getIdNum());
            iDNum = sb.replace(2, 12, "**********").toString();
            BeanUtils.copyProperties(personInfo, personInfoVO);
            personInfoVO.setIdNum(iDNum);
        } else {
            BeanUtils.copyProperties(personInfo, personInfoVO);
        }
        return personInfoVO;
    }
}
