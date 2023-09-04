package com.example.myoidpserver.service;

import com.example.myoidpserver.model.ClientInfo;
import com.example.myoidpserver.model.dto.GrantAccessToClientAppDto;
import com.example.myoidpserver.model.dto.RegisterClientAppDto;

public interface ClientInfoService {

    ClientInfo validateExistAndReturnByClientId(String clientId);

    String registerClient(RegisterClientAppDto dto);

    String grantAccessToClientApp(GrantAccessToClientAppDto dto);

}
