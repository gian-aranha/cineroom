package br.com.cineroom.api.entities;

import br.com.cineroom.api.dtos.user.UserDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity(name = "User")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String name;
    private Integer sessions;
    private Integer reviews;

    public User(UserDTO userDTO) {
        this.username = userDTO.username();
        this.email = userDTO.email();
        this.name = userDTO.name();
        this.sessions = 0;
        this.reviews = 0;
    }

}
