package com.ssafy.planner.plannershare.mapper;

import com.ssafy.planner.plannershare.dto.PlannerShare;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlannerShareMapper {

    void insertPlannerShare(PlannerShare share);

    PlannerShare findBySecretCode(String secretCode);

    PlannerShare findSecretCodeByPlannerId(Long plannerId);
}
