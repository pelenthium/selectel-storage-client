package com.github.pelenthium.selectel.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
    private boolean success;
    private String storageToken;
    private String authToken;
    private String storageUrl;
    private LocalDateTime date;
    private Long expireAuthToken;
}
