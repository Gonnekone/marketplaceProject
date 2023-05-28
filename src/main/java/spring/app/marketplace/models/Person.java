package spring.app.marketplace.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import spring.app.marketplace.util.Role;

import java.util.List;

@Entity
@Table(name = "user")
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
    @NotEmpty(message = "Email shouldn't be empty")
    @Email
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role shouldn't be empty")
    private Role role;

    @Column(name = "password")
    @NotEmpty(message = "Password shouldn't be empty")
    private String password;

    @OneToMany(mappedBy = "user_id")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Order> orders;

    @OneToOne(mappedBy = "owner")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private Bucket bucket;
}
