package com.finshark.backend.services.impls;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finshark.backend.dtos.user.AppUserDto;
import com.finshark.backend.dtos.user.CredentialsDto;
import com.finshark.backend.dtos.user.SignUpDto;
import com.finshark.backend.entities.AppUser;
import com.finshark.backend.exceptions.AppException;
import com.finshark.backend.mappers.AppUserMapper;
import com.finshark.backend.repositories.AppUserRepository;
import com.finshark.backend.services.AppUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService{
    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUserDto login(CredentialsDto credentialsDto) throws AppException {
        AppUser appUser = appUserRepository.findByUsername(credentialsDto.getUsername()).orElseThrow(() -> new AppException("Invalid login credentials", HttpStatus.UNAUTHORIZED));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), appUser.getPassword())) {
            return appUserMapper.toAppUserDto(appUser);
        }
        throw new AppException("Invalid login credentials", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public AppUserDto findByUsername(String username) throws AppException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return appUserMapper.toAppUserDto(appUser);
    }

    public AppUserDto register(SignUpDto signUpDto) throws AppException{
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(signUpDto.getUsername());

        if (optionalAppUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        AppUser appUser = AppUserMapper.signUpToAppUser(signUpDto);
        appUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.getPassword())));
        AppUser savedAppUser = appUserRepository.save(appUser);

        return appUserMapper.toAppUserDto(savedAppUser);
    }
}
