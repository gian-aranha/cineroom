package br.com.cineroom.api.entities;

import br.com.cineroom.api.dtos.session.SessionDTO;
import br.com.cineroom.api.utilities.Category;
import br.com.cineroom.api.utilities.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import br.com.cineroom.api.entities.Movie;;

@Table(name = "sessions")
@Entity(name = "Session")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    private Integer usersLimit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    private String content;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "movie_id")),
        @AttributeOverride(name = "title", column = @Column(name = "movie_title")),
        @AttributeOverride(name = "img", column = @Column(name = "movie_img")),
        @AttributeOverride(name = "overview", column = @Column(name = "movie_overview")),
        @AttributeOverride(name = "voteAverage", column = @Column(name = "movie_vote_average"))
    })
    private Movie movie;

    public Session(SessionDTO sessionDTO, User user) {
        this.code = sessionDTO.code();
        this.category = sessionDTO.category();
        this.usersLimit = sessionDTO.usersLimit();
        this.status = sessionDTO.status();
        this.content = sessionDTO.content();
        this.user = user;
    }

    public void updateFromDTO(SessionDTO sessionDTO, User user) {
        this.code = sessionDTO.code();
        this.category = sessionDTO.category();
        this.usersLimit = sessionDTO.usersLimit();
        this.status = sessionDTO.status();
        this.content = sessionDTO.content();
        this.user = user;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            this.createdAt = LocalDateTime.now();
            return;
        }
        this.createdAt = createdAt;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
