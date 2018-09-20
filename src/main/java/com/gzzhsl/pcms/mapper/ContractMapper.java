package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.Contract;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractMapper {
    int deleteByPrimaryKey(String id);

    int insert(Contract record);

    Contract selectByPrimaryKey(String id);

    List<Contract> selectAll();

    int updateByPrimaryKey(Contract record);

    List<Contract> findPageByBaseInfoIdAndState(@Param("baseInfoId") String baseInfoId, @Param("state") byte state);

    Contract findWithImgById(String id);

    List<Contract> findWithImgByBaseInfoId(@Param("baseInfoId") String baseInfoId);
}