package com.finshark.backend.mappers;

import org.mapstruct.Mapper;

import com.finshark.backend.dtos.user.AppUserDto;
import com.finshark.backend.dtos.user.SignUpDto;
import com.finshark.backend.entities.AppUser;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserDto toAppUserDto(AppUser stock);

    static AppUser signUpToAppUser(SignUpDto signUpDto) {
        AppUser appUser = new AppUser();
        appUser.setFirstName(signUpDto.getFirstName());
        appUser.setLastName(signUpDto.getLastName());
        appUser.setUsername(signUpDto.getUsername());
        appUser.setEmail(signUpDto.getEmail());
        return appUser;
    }
}
