package br.com.cineroom.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        String name
) {
}
