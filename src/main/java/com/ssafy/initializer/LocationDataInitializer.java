package com.ssafy.initializer;

import com.ssafy.location.api.LocationDataClient;
import com.ssafy.location.dto.Gugun;
import com.ssafy.location.dto.Sido;
import com.ssafy.location.service.LocationService;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationDataInitializer implements ApplicationRunner {

  private final LocationService locationService;
  private final LocationDataClient locationDataClient;
  private final DataSource dataSource;

  /**
   * 애플리케이션 시작 시 API로부터 시도 및 구군 데이터를 초기화합니다.
   */
  @Override
  public void run(ApplicationArguments args) {
    try {
      log.info("[INIT] Start to load initial data");

      // 시도 정보가 DB에 없는 경우에만 호출
      if (locationService.getAllSidos()
          .isEmpty()) {
        log.info("[INIT] Sido table empty. Fetching from API...");
        log.info("[INIT] Start to load Sidos...");

        // 시도 정보 불러오기
        int idx = 1;
        List<Integer> codeList = new ArrayList<>();
        while (true) {
          List<Sido> sidoList = locationDataClient.loadSidos(idx++);
          if (sidoList == null || sidoList.isEmpty()) {
            break;
          }

          log.info("[INIT] Save Sidos… count={}", sidoList.size());
          locationService.insertAllSidos(sidoList);
          for (Sido sido : sidoList) {
            codeList.add(sido.getSidoCode());
          }
        }

        log.info("[INIT] Start to load Guguns...");
        // 구군 정보 불러오기
        for (int code : codeList) {
          idx = 1;
          while (true) {
            List<Gugun> gugunList = locationDataClient.loadGuguns(code, idx++);
            if (gugunList == null || gugunList.isEmpty()) {
              break;
            }

            log.info("[INIT] Save Guguns for sido={}… count={}", code, gugunList.size());
            gugunList.forEach(gugun -> gugun.setSidoCode(code));
            locationService.insertAllGuguns(gugunList);
          }
        }
        log.info("[INIT] Data initialization completed.");
      } else {
        log.info("[INIT] Data already exists. Skipping API fetch.");
      }

      log.info("[INIT] Now loading dummy data...");
      ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
      pop.addScript(new ClassPathResource("data.sql"));
      pop.setSeparator(";");
      pop.execute(dataSource);
      log.info("[INIT] data.sql has been applied.");

    } catch (Exception e) {
      log.error("[INIT:ERROR] Data initialization failed: {}", e.getMessage(), e);
    }
  }
}
