package com.ssafy.location.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sido {

  private Long id;
  @JsonProperty("code")
  private Integer sidoCode;
  @JsonProperty("name")
  private String sidoName;
}
