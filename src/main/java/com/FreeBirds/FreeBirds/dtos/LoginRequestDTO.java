package com.FreeBirds.FreeBirds.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Required")
    private String email; // Or email
    @NotBlank(message = "Required")
    private String password;

}