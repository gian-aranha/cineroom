package br.com.cineroom.api.dtos.review;

import br.com.cineroom.api.entities.Review;

import java.time.LocalDateTime;

public record ReviewReturnDTO(
    Long id,
    Long userId,
    Long sessionId,
    String content,
    LocalDateTime createdAt,
    Integer rating,
    String comment
) {

    public ReviewReturnDTO(Review review) {
        this(
                review.getId(),
                review.getUser().getId(),
                review.getSession().getId(),
                review.getContent(),
                review.getCreatedAt(),
                review.getRating(),
                review.getComment()
        );
    }

}
