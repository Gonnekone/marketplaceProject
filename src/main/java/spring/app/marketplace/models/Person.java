package spring.app.marketplace.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "client")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    @NotEmpty(message = "Login shouldn't be empty")
    @Email
    private String email;

    @Column(name = "role")
    @NotEmpty(message = "Role shouldn't be empty")
    private String role;

    @Column(name = "password")
    @NotEmpty(message = "Password shouldn't be empty")
    private String password;
}
