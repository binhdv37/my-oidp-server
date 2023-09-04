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
public class GrantAccessToClientAppDto {
    private String clientId;
    private Set<String> scopes;
    private Long expiredTime;
}
