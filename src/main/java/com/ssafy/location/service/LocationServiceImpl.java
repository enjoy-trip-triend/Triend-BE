package com.ssafy.location.service;

import com.ssafy.location.dto.Gugun;
import com.ssafy.location.dto.Sido;
import com.ssafy.location.mapper.GugunMapper;
import com.ssafy.location.mapper.SidoMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

  private final SidoMapper sidoMapper;
  private final GugunMapper gugunMapper;

  @Transactional
  @Override
  public void insertAllSidos(List<Sido> list) {
    sidoMapper.insertAll(list);
  }

  @Override
  public List<Sido> getAllSidos() {
    return sidoMapper.getAll();
  }

  @Transactional
  @Override
  public void insertAllGuguns(List<Gugun> list) {
    gugunMapper.insertAll(list);
  }

  @Override
  public List<Gugun> getGugunBySido(Integer sidoCode) {
    return gugunMapper.findBySidoCode(sidoCode);
  }
}