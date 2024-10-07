package br.com.cineroom.api.dtos.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewDTO(
    @NotBlank
    Long userId,
    @NotBlank
    Long sessionId,
    @NotBlank
    String content,
    @NotNull
    Integer rating,
    @NotBlank
    String comment
) {
}
