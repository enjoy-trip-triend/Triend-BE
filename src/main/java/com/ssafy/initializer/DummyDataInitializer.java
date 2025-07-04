package com.ssafy.initializer;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class DummyDataInitializer implements ApplicationRunner {

  private final DataSource dataSource;

  /**
   * 애플리케이션 시작 시 data.sql로부터 더미 데이터를 초기화합니다.
   */
  @Override
  public void run(ApplicationArguments args) {
    try {
      log.info("[INIT] Now loading Dummy Data...");
      ResourceDatabasePopulator pop = new ResourceDatabasePopulator();
      pop.addScript(new ClassPathResource("data.sql"));
      pop.setSeparator(";");
      pop.execute(dataSource);
      log.info("[INIT] data.sql has been applied.");
    } catch (Exception e) {
      log.error("[INIT:ERROR] Dummy Data initialization FAILED: {}", e.getMessage(), e);
    }
  }
}
