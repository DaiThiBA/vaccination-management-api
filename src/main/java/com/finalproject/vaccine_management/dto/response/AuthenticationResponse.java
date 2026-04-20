package com.finalproject.vaccine_management.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private boolean authenticated;
    private String token;
}
