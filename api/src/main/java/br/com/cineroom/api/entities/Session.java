package br.com.cineroom.api.entities;

import br.com.cineroom.api.utilities.Category;
import br.com.cineroom.api.utilities.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

@Table(name = "sessions")
@Entity(name = "Session")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
}
