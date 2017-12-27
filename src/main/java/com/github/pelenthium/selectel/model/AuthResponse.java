package com.github.pelenthium.selectel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
