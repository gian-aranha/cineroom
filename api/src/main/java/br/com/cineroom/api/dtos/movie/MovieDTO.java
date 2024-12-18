package br.com.cineroom.api.dtos.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovieDTO(
        @NotBlank
        String title,
        @NotBlank
        String image,
        @NotBlank
        String overview,
        @NotNull
        Double rating
) {
}
