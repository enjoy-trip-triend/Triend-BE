package com.ssafy.plannershare.mapper;

import com.ssafy.plannershare.dto.PlannerShare;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlannerShareMapper {

    void insertPlannerShare(PlannerShare share);
}
