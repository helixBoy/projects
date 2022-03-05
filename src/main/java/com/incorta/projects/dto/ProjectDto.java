package com.incorta.projects.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
    private String name;
    private String state;
    private double progress;
    private Long ownerId;
}
