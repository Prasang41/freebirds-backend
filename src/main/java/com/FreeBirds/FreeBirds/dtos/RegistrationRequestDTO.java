package com.FreeBirds.FreeBirds.dtos;

import com.FreeBirds.FreeBirds.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDTO {
    @NotBlank(message = "Required")
    private String firstName;
    @NotBlank(message = "Required")
    private String lastName;
    @NotBlank(message = "Required")
    private String email;
    @NotBlank(message = "Required")
    private String phoneNumber;
    @NotBlank(message = "Required")
    private UserRole role;
    @NotBlank(message = "Required")
    private String password;
// Plain password, hashed in service
}