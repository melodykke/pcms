package com.gzzhsl.pcms.converter;

import com.gzzhsl.pcms.entity.PersonInfo;
import com.gzzhsl.pcms.form.PersonInfoForm;
import com.gzzhsl.pcms.vo.PersonInfoVO;
import org.springframework.beans.BeanUtils;

public class PersonInfo2VO {
    public static PersonInfoVO convert(PersonInfo personInfo) {
        PersonInfoVO personInfoVO = new PersonInfoVO();
        if (personInfo.getId_num() != null || personInfo.getId_num() != "") {
            String iDNum = personInfo.getId_num();
        // 隐藏身份证部分位置数字
            StringBuilder sb = new StringBuilder(personInfo.getId_num());
            iDNum = sb.replace(2, 12, "**********").toString();
            BeanUtils.copyProperties(personInfo, personInfoVO);
            personInfoVO.setId_num(iDNum);
        } else {
            BeanUtils.copyProperties(personInfo, personInfoVO);
        }
        return personInfoVO;
    }
}
