package br.com.cineroom.api.dtos.credential;

import jakarta.validation.constraints.NotBlank;

public record CredentialDTO(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
