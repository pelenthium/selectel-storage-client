package com.github.pelenthium.selectel.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountInfoResponse {
    private boolean success;
    private int objectCount;
    private int containerCount;
    private long byteUsed;
}
