package spring.app.marketplace.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
