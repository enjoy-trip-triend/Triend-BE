package com.ssafy.location.mapper;

import com.ssafy.location.dto.Sido;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SidoMapper {

  void insertAll(@Param("list") List<Sido> list);

  List<Sido> getAll();

}
