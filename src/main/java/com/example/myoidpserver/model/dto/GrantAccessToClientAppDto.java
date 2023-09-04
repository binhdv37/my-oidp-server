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
    private String client_id;
    private String scope; // http url param encoded
    private Long expiredTime;

    private String response_type;
    private String redirect_url;
    private String state;
}
