package com.finshark.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finshark.backend.configs.UserAuthenticationProvider;
import com.finshark.backend.dtos.user.AppUserDto;
import com.finshark.backend.dtos.user.CredentialsDto;
import com.finshark.backend.dtos.user.SignUpDto;
import com.finshark.backend.services.AppUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AppUserService appUserService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<AppUserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        AppUserDto appUserDto = appUserService.login(credentialsDto);
        appUserDto.setToken(userAuthenticationProvider.createToken(appUserDto.getUsername()));
        return ResponseEntity.ok(appUserDto);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserDto> register(@RequestBody @Valid SignUpDto appUser) {
        AppUserDto createdUser = appUserService.register(appUser);
        createdUser.setToken(userAuthenticationProvider.createToken(appUser.getUsername()));
        return ResponseEntity.ok(createdUser);
    }

}
