package com.ssafy.planner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.planner.dto.Planner;

@Mapper
public interface PlannerMapper {
	
	int createPlanner(Planner planner);

	List<Planner> getPlannersByMember(Long memberId);
	
	Planner getPlannerById(Long plannerId);

	int updatePlanner(Planner planner);

	int deletePlanner(Long plannerId);
}
