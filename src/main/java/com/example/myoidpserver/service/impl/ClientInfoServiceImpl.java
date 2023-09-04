package com.example.myoidpserver.service.impl;

import com.example.myoidpserver.model.*;
import com.example.myoidpserver.model.dto.GrantAccessToClientAppDto;
import com.example.myoidpserver.model.dto.RegisterClientAppDto;
import com.example.myoidpserver.repository.*;
import com.example.myoidpserver.service.ClientInfoService;
import com.example.myoidpserver.service.CurrentUserService;
import com.sunteco.suncloud.lib.exception.StcException;
import com.sunteco.suncloud.lib.model.UserInfo;
import io.netty.handler.codec.base64.Base64Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ClientInfoServiceImpl implements ClientInfoService {

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Autowired
    private ClientRedirectUrlRepository clientRedirectUrlRepository;

    @Autowired
    private ClientScopeRepository clientScopeRepository;

    @Autowired
    private SystemScopeRepository systemScopeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApprovedSiteRepository approvedSiteRepository;

    @Autowired
    private ApprovedSiteScopeRepository approvedSiteScopeRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Override
    public ClientInfo validateExistAndReturnByClientId(String clientId) {
        ClientInfo clientInfo = clientInfoRepository.findByClientId(clientId);
        if (clientInfo == null) {
            throw new StcException("CLIENT_INFO_NOT_FOUND", "CLIENT_INFO_NOT_FOUND");
        }
        return clientInfo;
    }

    @Transactional
    @Override
    public String registerClient(RegisterClientAppDto dto) {
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setName(dto.getClientName());
        String clientId = UUID.randomUUID().toString();
        clientInfo.setClientId(clientId);
        clientInfo = clientInfoRepository.save(clientInfo);
        Long savedClientInfoId = clientInfo.getId();

        Set<ClientRedirectUrl> redirectUrls = new HashSet<>();
        for (String url : dto.getRedirectUrls()) {
            ClientRedirectUrl c = new ClientRedirectUrl();
            c.setClientInfo(clientInfo);
            c.setUrl(url);
            redirectUrls.add(c);
        }
        clientRedirectUrlRepository.saveAll(redirectUrls);

        List<SystemScope> systemScopes = systemScopeRepository.findAllByScopeIn(new ArrayList<>(dto.getScopes()));
        Set<ClientScope> clientScopes = new HashSet<>();
        for (String scope : dto.getScopes()) {
            SystemScope systemScope = systemScopes.stream().filter(s -> s.getScope().equals(scope)).findFirst().orElse(null);
            if (systemScope != null) {
                ClientScope clientScope = new ClientScope();
                ClientScopeId clientScopeId = new ClientScopeId();
                clientScopeId.setScopeId(systemScope.getId());
                clientScopeId.setClientInfoId(savedClientInfoId);
                clientScope.setClientScopeId(clientScopeId);

                clientScope.setClientInfo(clientInfo);
                clientScope.setSystemScope(systemScope);

                clientScopes.add(clientScope);
            }
        }
        clientScopeRepository.saveAll(clientScopes);
        return clientId;
    }

    @Transactional
    @Override
    public String grantAccessToClientApp(GrantAccessToClientAppDto dto) {
        ApprovedSite approvedSite = new ApprovedSite();
        UserInfo userInfo = currentUserService.getCurrentUser();
        User user = userRepository.getById(userInfo.getId());
        approvedSite.setUser(user);
        ClientInfo clientInfo = this.validateExistAndReturnByClientId(dto.getClient_id());
        approvedSite.setClientInfo(clientInfo);
        approvedSite.setApprovedDate(new Date());
        if (dto.getExpiredTime() != null) approvedSite.setExpiredDate(new Date(dto.getExpiredTime() * 1000));
        approvedSite = approvedSiteRepository.save(approvedSite);

        List<ApprovedSiteScope> approvedSiteScopes = new ArrayList<>();
        List<SystemScope> systemScopes = systemScopeRepository.findAllByScopeIn(new ArrayList<>(dto.getScopes()));
        for (String scope : dto.getScopes()) {
            SystemScope systemScope = systemScopes.stream().filter(s -> s.getScope().equals(scope)).findFirst().orElse(null);
            if (systemScope != null) {
                ApprovedSiteScope approvedSiteScope = new ApprovedSiteScope();
                ApprovedSiteScopeId id = new ApprovedSiteScopeId();
                id.setScopeId(systemScope.getId());
                id.setApprovedSiteId(approvedSite.getId());
                approvedSiteScope.setId(id);
                approvedSiteScope.setSystemScope(systemScope);
                approvedSiteScope.setApprovedSite(approvedSite);
                approvedSiteScopes.add(approvedSiteScope);
            }
        }
        approvedSiteScopeRepository.saveAll(approvedSiteScopes);

        return this.generateAuthorizatonCode();
    }

    private String generateAuthorizatonCode() {
        return Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
    }

}
