package br.com.cineroom.api.dtos;

import br.com.cineroom.api.entities.User;

public record UserReturnDTO(
        Long id,
        String username,
        String email,
        String name,
        Integer sessions,
        Integer reviews
) {

    public UserReturnDTO(User user){
        this(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getName(),
                user.getSessions(),
                user.getReviews()
        );
    }

}
