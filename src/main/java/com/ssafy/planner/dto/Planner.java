package com.ssafy.planner.dto;

import com.ssafy.member.dto.Member;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Planner {

  private Long id;
  private LocalDate startDay;
  private LocalDate endDay;
  private Long memberId;
  private String name;
  private String location;
  private String comment;
  private Exposure exposure;
  private String password;
  private Long likesCount;
  private Member member;
}
