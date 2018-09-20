package com.gzzhsl.pcms.mapper;

import com.gzzhsl.pcms.model.TimeLineItem;
import java.util.List;

public interface TimeLineItemMapper {
    int deleteByPrimaryKey(Integer timeLineItemId);

    int insert(TimeLineItem record);

    TimeLineItem selectByPrimaryKey(Integer timeLineItemId);

    List<TimeLineItem> selectAll();

    int updateByPrimaryKey(TimeLineItem record);
}