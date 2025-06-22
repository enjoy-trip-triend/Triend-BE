package com.ssafy.location.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ssafy.location.dto.Gugun;
import com.ssafy.location.dto.Sido;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationDataClient {

  @Value("${location.api.key}")
  private String apiKey;

  @Value("${location.api.domain}")
  private String domain;

  private final ObjectMapper objectMapper;

  /**
   * 시도(Sido) 정보를 API에서 조회해 Sido 리스트로 반환합니다.
   * @param pageNo 조회할 페이지 번호 (1~)
   * @return Sido 리스트 (오류 시 빈 리스트)
   */
  public List<Sido> loadSidos(int pageNo) {
    try {
      String url = buildUrl("/areaCode2", Map.of(
          "MobileOS", "ETC",
          "MobileApp", "Triend",
          "numOfRows", "10",
          "pageNo", String.valueOf(pageNo),
          "_type", "json"
      ));
      JsonArray list = fetchJsonArray(url);
      return Arrays.asList(objectMapper.readValue(list.toString(), Sido[].class));
    } catch (Exception e) {
      System.out.println("[ERROR] Failed to fetch Sido data : " + e.getMessage());
      return Collections.emptyList();
    }
  }

  /**
   * 구군(Gugun) 정보를 API에서 조회해 Gugun 리스트로 반환합니다.
   * @param sido 상위 시도 코드
   * @param pageNo 조회할 페이지 번호 (1~)
   * @return Gugun 리스트 (오류 시 빈 리스트)
   */
  public List<Gugun> loadGuguns(int sido, int pageNo) {
    try {
      String url = buildUrl("/areaCode2", Map.of(
          "areaCode", String.valueOf(sido),
          "MobileOS", "ETC",
          "MobileApp", "Triend",
          "numOfRows", "10",
          "pageNo", String.valueOf(pageNo),
          "_type", "json"
      ));
      JsonArray list = fetchJsonArray(url);
      return Arrays.asList(objectMapper.readValue(list.toString(), Gugun[].class));
    } catch (Exception e) {
      System.out.println("[ERROR] Failed to fetch Gugun data: " + e.getMessage());
      return Collections.emptyList();
    }
  }

  /**
   * API 호출을 위해 URL을 생성합니다.
   * @param endpoint 호출할 엔드포인트 경로
   * @param params 쿼리 파라미터 맵
   * @return 완성된 URL 문자열
   * @throws Exception URL 인코딩 오류
   */
  private String buildUrl(String endpoint, Map<String, String> params) throws Exception {
    StringBuilder builder = new StringBuilder(domain + endpoint);
    builder.append("?serviceKey=")
        .append(apiKey);
    for (Map.Entry<String, String> entry : params.entrySet()) {
      builder.append("&")
          .append(URLEncoder.encode(entry.getKey(), "UTF-8"))
          .append("=")
          .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
    }
    //System.out.println(builder.toString());
    return builder.toString();
  }

  /**
   * URL에 GET 요청을 보내고 JSON 응답으로부터 items.item 배열을 추출해 반환합니다. items가 없거나 객체가 아니면 빈 배열을 반환합니다.
   * @param urlStr 호출할 URL
   * @return JsonArray 형태의 결과
   * @throws Exception 네트워크/파싱 오류
   */
  private JsonArray fetchJsonArray(String urlStr) throws Exception {
    HttpURLConnection conn = getConnection(urlStr);
    if (conn == null) {
      return new JsonArray();
    }

    // 응답 생성
    StringBuilder sb = new StringBuilder();
    try (var br = new BufferedReader(
        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
    }
    // root 파싱
    JsonObject root = JsonParser.parseString(sb.toString())
        .getAsJsonObject();
    JsonObject response = root.getAsJsonObject("response");
    JsonObject body = response.getAsJsonObject("body");

    // items 필드 가져오기
    JsonElement itemsElem = body.get("items");
    if (itemsElem == null || !itemsElem.isJsonObject()) {
      return new JsonArray();
    }

    // items.item 추출
    JsonObject itemsObj = itemsElem.getAsJsonObject();
    JsonElement itemElem = itemsObj.get("item");

    JsonArray list = new JsonArray();
    if (itemElem == null) {
      return list;
    } else if (itemElem.isJsonArray()) {
      return itemElem.getAsJsonArray();
    } else {
      list.add(itemElem.getAsJsonObject());
      return list;
    }
  }

  /**
   * HTTP GET 연결을 생성합니다. 헤더를 통해 JSON 응답을 요청합니다.
   * @param link 연결할 URL
   * @return HttpURLConnection 객체 (실패 시 null)
   */
  private HttpURLConnection getConnection(String link) {
    try {
      URL url = new URL(link);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");
      conn.setConnectTimeout(10000);
      conn.setReadTimeout(10000);
      conn.connect();
      return conn;
    } catch (Exception e) {
      return null;
    }
  }

}