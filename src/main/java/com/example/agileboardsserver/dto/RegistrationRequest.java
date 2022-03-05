package com.example.agileboardsserver.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
