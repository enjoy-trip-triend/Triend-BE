package com.ssafy.plan.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.plan.dto.Plan;
import com.ssafy.plan.dto.PlanImage;

@Mapper
public interface PlanMapper {
	int createPlan(Plan planner);

	Plan getPlanById(Long planId);

	List<PlanImage> findImagesByPlanIds(@Param("planIds") List<Long> planIds);
	
	int updatePlan(Plan plan);

	int deletePlan(Long planId);
	
	int deletePlansByPlannerAndDate(Long plannerId, LocalDate startDay, LocalDate endDay);
	
	int deletePlansByPlanner(Long plannerId);
	
	List<Plan> getPlansByPlanner(Long plannerId);
	
	int updatePlansBatch(List<Plan> plans);
	
	int deletePlansBatch(List<Long> plansIdList);
	
	void insertPlanImages(@Param("planId") Long planId, @Param("imageKeys") List<String> imageKeys);
}
