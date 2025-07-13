package com.ssafy.location.controller;

import com.ssafy.location.dto.Gugun;
import com.ssafy.location.dto.Sido;
import com.ssafy.location.service.LocationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class LocationController {

  private final LocationService locationService;

  @GetMapping("/sidos")
  public ResponseEntity<List<Sido>> getSidos() {
    return ResponseEntity.ok(locationService.getAllSidos());
  }

  @GetMapping("/guguns/{sido-code}")
  public ResponseEntity<List<Gugun>> getGuguns(@PathVariable("sido-code") Integer sidoCode) {
    return ResponseEntity.ok(locationService.getGugunBySido(sidoCode));
  }
}