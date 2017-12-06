package com.github.pelenthium.selectel.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContainerListResponse {
    private boolean success;
    private List<ContainerResponse> containers;
    private int count;
}
