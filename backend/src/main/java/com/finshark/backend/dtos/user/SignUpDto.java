package com.finshark.backend.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpDto {
    
    @NotEmpty
    private String username;

    @NotEmpty
    private String email;

    @NotEmpty
    private char[] password;


}