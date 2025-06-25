package com.ssafy.planner.mapper;

import com.ssafy.planner.dto.PlannerLocationDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlannerLocationMapper {

  int insertLocation(Long plannerId, List<PlannerLocationDto> list);

}
