package com.example.myoidpserver.service.impl;

import com.example.myoidpserver.model.User;
import com.example.myoidpserver.repository.UserRepository;
import com.sunteco.suncloud.lib.Constant.MessageCode;
import com.sunteco.suncloud.lib.exception.StcException;
import com.sunteco.suncloud.lib.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByEmailOrUsername(s, s).orElseThrow(() ->
                new StcException("USER_NOT_FOUND", "USER_NOT_FOUND"));
        if (user.getPassword() == null) {
            throw new StcException("INCORRECT_USERNAME_OR_PASSWORD", "INCORRECT_USERNAME_OR_PASSWORD");
        }
        UserInfo result = new UserInfo(user.getUsername(), user.getPassword());
        result.setId(user.getId());
        result.setUsername(user.getUsername());
        result.setFullName(user.getName());
        result.setEmail(user.getEmail());
        result.setPhoneNumber(user.getPhone());
        return result;
    }

}
