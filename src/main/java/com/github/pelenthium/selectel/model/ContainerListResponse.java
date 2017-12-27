package com.github.pelenthium.selectel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ContainerListResponse {
    private boolean success;
    private List<ContainerResponse> containers;
    private int count;
}
