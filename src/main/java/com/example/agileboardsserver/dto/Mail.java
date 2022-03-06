package com.example.agileboardsserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mail {
    private String subject;
    private String recipient;
    private String body;
}
