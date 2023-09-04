package com.example.myoidpserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterClientAppDto {
    private String clientName;
    private Set<String> redirectUrls;
    private Set<String> scopes;
}
