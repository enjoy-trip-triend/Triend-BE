package com.ssafy.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageInfoDTO {
    private AIRole role;
    private String content;
    private Long timestamp;
}
