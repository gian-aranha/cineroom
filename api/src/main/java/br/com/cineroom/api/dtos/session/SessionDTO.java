package br.com.cineroom.api.dtos.session;

import br.com.cineroom.api.entities.User;
import br.com.cineroom.api.utilities.Category;
import br.com.cineroom.api.utilities.Status;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

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
    @Nullable
    LocalDateTime createdAt,
    @NotNull(message = "User cannot be null")
    User user,
    Long movieId
){
}
