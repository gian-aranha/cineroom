package br.com.cineroom.api.dtos.session;

import br.com.cineroom.api.entities.User;
import br.com.cineroom.api.utilities.Category;
import br.com.cineroom.api.utilities.Status;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record SessionDTO(
    @NotBlank
    String code,
    @NotBlank
    Category category,
    @NotBlank
    Integer usersLimit,
    @NotBlank
    Status status,
    @NotBlank
    String content,
    @NotBlank
    LocalDateTime createdAt,
    @NotBlank
    User user
){
}
