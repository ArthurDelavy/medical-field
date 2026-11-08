package com.medicalfield.core.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.medicalfield.core.auth.dto.LoginRequest;
import com.medicalfield.core.security.TokenService;
import com.medicalfield.core.user.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class AuthService {

    private final AuthenticationManager authMgr;

    private final TokenService tknSvc;



    public String authUser(@Valid LoginRequest data) {
        
        UsernamePasswordAuthenticationToken usrPswTkn = new UsernamePasswordAuthenticationToken(data.identity(), data.password());

        Authentication auth = authMgr.authenticate(usrPswTkn);


        User user = (User) auth.getPrincipal();

        return tknSvc.generateToken(user);
    }

}
