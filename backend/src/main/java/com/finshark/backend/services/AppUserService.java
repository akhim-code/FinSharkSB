package com.finshark.backend.services;

import com.finshark.backend.dtos.user.AppUserDto;
import com.finshark.backend.dtos.user.CredentialsDto;
import com.finshark.backend.dtos.user.SignUpDto;

public interface AppUserService {
    AppUserDto login(CredentialsDto credentialsDto);
    AppUserDto findByUsername(String username);
    AppUserDto register(SignUpDto userDto);
}
