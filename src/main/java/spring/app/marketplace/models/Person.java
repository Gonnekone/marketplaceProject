package spring.app.marketplace.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import spring.app.marketplace.util.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"user\"")
@Data
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

    @OneToMany(mappedBy = "owner")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Order> orders;

    @OneToOne(mappedBy = "owner")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private Bucket bucket;

    public void addOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }

        orders.add(order);
    }

    public void clearBucket() {
        bucket.setGoods(new ArrayList<>());
    }
}
