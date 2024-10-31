package br.com.cineroom.api.entities;

import br.com.cineroom.api.dtos.review.ReviewDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "reviews")
@Entity(name = "Review")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private Session session;

    private String content;
    private LocalDateTime createdAt;
    private Integer rating;
    private String comment;

    public Review(ReviewDTO reviewDTO, User user, Session session) {
        this.user = user;
        this.session = session;
        this.content = reviewDTO.content();
        this.createdAt = LocalDateTime.now();
        this.rating = reviewDTO.rating();
        this.comment = reviewDTO.comment();
    }

    public void updateReview(ReviewDTO reviewDTO) {
        this.content = reviewDTO.content();
        this.rating = reviewDTO.rating();
        this.comment = reviewDTO.comment();
    }

}
