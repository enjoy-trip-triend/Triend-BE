package com.ssafy.planner.mapper;

import com.ssafy.planner.dto.PlannerLocationDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlannerLocationMapper {

  int insertLocation(@Param("plannerId") Long plannerId, @Param("list") List<PlannerLocationDto> list);

  void deleteLocation(@Param("plannerId") Long PlannerId);
}