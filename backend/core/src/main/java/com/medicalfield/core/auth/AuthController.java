package com.medicalfield.core.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medicalfield.core.auth.dto.LoginRequest;
import com.medicalfield.core.auth.dto.RegisterRequest;
import com.medicalfield.core.auth.dto.TokenResponse;
import com.medicalfield.core.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor 
@Tag(name = "Auth", description = "Autenticação de usuários")
public class AuthController {
 
    private final AuthService authSvc;
    private final UserService usrSvc;


    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário")
        @ApiResponse(responseCode = "200", description = "Logado com sucesso!")
        @ApiResponse(responseCode = "400", description = "Um ou mais campos estão com os valores inválidos", content = @Content)
        @ApiResponse(responseCode = "400", description = "Usuário e/ou senha inválidos", content = @Content)
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest data) {

        TokenResponse responseData = new TokenResponse(authSvc.authUser(data));

        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }


    @Operation(summary = "Criar novo usuário")
        @ApiResponse(responseCode = "201", description = "Criado com sucesso!")
        @ApiResponse(responseCode = "400", description = "Um ou mais campos estão com os valores inválidos", content = @Content)
        @ApiResponse(responseCode = "409", description = "Identificação e/ou e-mail em uso", content = @Content)
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest data) {
        usrSvc.registerUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
