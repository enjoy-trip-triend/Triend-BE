package com.ssafy.location.service;

import com.ssafy.location.dto.Gugun;
import com.ssafy.location.dto.Sido;
import java.util.List;

public interface LocationService {

  void insertAllSidos(List<Sido> list);

  List<Sido> getAllSidos();

  void insertAllGuguns(List<Gugun> list);

  List<Gugun> getGugunBySido(Integer sidoCode);


}