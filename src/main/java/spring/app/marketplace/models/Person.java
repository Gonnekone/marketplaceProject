package spring.app.marketplace.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import spring.app.marketplace.util.Role;

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
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "password")
    @NotEmpty(message = "Password shouldn't be empty")
    private String password;
}
