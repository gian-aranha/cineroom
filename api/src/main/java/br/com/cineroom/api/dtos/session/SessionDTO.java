package br.com.cineroom.api.dtos.session;

import br.com.cineroom.api.utilities.Category;
import br.com.cineroom.api.utilities.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SessionDTO(
    @NotBlank
    String code,
    @NotNull(message = "Category cannot be null")
    Category category,
    @NotNull(message = "userLimit cannot be null")
    Integer usersLimit,
    @NotNull(message = "Status cannot be null")
    Status status,
    @NotBlank
    String content,
    @NotNull(message = "User ID cannot be null")
    Long userId,
    Long movieId
){
}
