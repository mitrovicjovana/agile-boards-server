package com.example.agileboardsserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectDto {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private ProjectUserDto user;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ProjectUserDto{
        private UUID id;
        private String firstName;
        private String lastName;
        private String username;
    }
}
