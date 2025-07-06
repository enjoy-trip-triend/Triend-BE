package com.ssafy.planner.plannershare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlannerShare {
    private Long id;
    private String secretCode;
    private String password;
    private Long plannerId;

    public boolean isPasswordMatched(String inputPassword, PasswordEncoder encoder) {
        return encoder.matches(inputPassword, this.password);
    }
}
