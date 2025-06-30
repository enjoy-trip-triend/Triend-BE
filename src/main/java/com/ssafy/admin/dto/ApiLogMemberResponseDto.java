package com.ssafy.admin.dto;

import com.ssafy.member.dto.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiLogMemberResponseDto {
    
    private Long id;
    private String email;
    private String name;
    private Role role;
    
}
