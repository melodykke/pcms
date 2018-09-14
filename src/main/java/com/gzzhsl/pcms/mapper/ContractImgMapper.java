package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.ContractImg;
import java.util.List;

public interface ContractImgMapper {
    int deleteByPrimaryKey(String contractImgId);

    int insert(ContractImg record);

    ContractImg selectByPrimaryKey(String contractImgId);

    List<ContractImg> selectAll();

    int updateByPrimaryKey(ContractImg record);
}