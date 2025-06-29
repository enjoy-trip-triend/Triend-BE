package com.ssafy.location.mapper;

import com.ssafy.location.dto.Gugun;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GugunMapper {

  void insertAll(@Param("list") List<Gugun> list);

  List<Gugun> findBySidoCode(@Param("sidoCode") Integer sidoCode);

}
