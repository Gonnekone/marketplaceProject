package spring.app.marketplace.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import spring.app.marketplace.util.Status;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "\"order\"")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Date of creation shouldn't be empty")
    private LocalDateTime created_at;

    @Column(name = "changed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime changed_at;

    @Column(name = "status")
    @NotNull(message = "Status shouldn't be empty")
    private Status status;

    @NotNull(message = "Owner shouldn't be empty")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Person owner;

    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    @JoinTable(
            name = "order_good_amount",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "good_id")
    )
    private List<Good> goods;
}
