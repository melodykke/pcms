package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.Contract;
import java.util.List;

public interface ContractMapper {
    int deleteByPrimaryKey(String id);

    int insert(Contract record);

    Contract selectByPrimaryKey(String id);

    List<Contract> selectAll();

    int updateByPrimaryKey(Contract record);

    List<Contract> findPageByBaseInfoIdAndState(String baseInfoId, byte state);

    Contract findWithImgById(String id);
}