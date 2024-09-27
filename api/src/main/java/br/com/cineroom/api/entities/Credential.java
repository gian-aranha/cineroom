package br.com.cineroom.api.entities;

import br.com.cineroom.api.dtos.user.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "credentials")
@Entity(name = "Credential")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Credential(User user, UserDTO userDTO) {
        this.email = userDTO.email();
        this.password = userDTO.password();
        this.user = user;
    }

}
