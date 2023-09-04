package com.example.myoidpserver.controller.admin;

import com.example.myoidpserver.model.dto.RegisterClientAppDto;
import com.example.myoidpserver.service.ClientInfoService;
import com.sunteco.suncloud.lib.model.dto.shared.StcSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/admin/client-info")
public class AdminClientInfoController {

    @Autowired
    private ClientInfoService clientInfoService;

    @PostMapping("/register")
    public StcSuccessResponse<String> registerClientApp(@RequestBody RegisterClientAppDto dto) {
        return new StcSuccessResponse<>(clientInfoService.registerClient(dto));
    }

}
