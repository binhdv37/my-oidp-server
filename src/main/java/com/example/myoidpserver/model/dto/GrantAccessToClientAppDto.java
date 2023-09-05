package com.example.myoidpserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrantAccessToClientAppDto {
    private String clientId;
    private String scope; // http url param encoded
    private Long expiredTime;
    private String responseType;
    private String redirectUrl;
    private String state;
}
