package com.ssafy.planner.plannershare.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlannerMemberMapper {

    boolean isPlannerMember(Long plannerId, Long memberId);

    void insertPlannerMember(Long plannerId, Long memberId);
}
