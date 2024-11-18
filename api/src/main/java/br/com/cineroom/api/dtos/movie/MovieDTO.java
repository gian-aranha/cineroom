package br.com.cineroom.api.dtos.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovieDTO(
    @NotNull Long id,
    @NotBlank String title,
    @NotBlank String img,
    @NotBlank String overview,
    @NotNull Double voteAverage
) {}

