package spring.app.marketplace.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bucket")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bucket {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Owner shouldn't be empty")
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Person owner;

    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    @JoinTable(
            name = "good_bucket_amount",
            joinColumns = @JoinColumn(name = "bucket_id"),
            inverseJoinColumns = @JoinColumn(name = "good_id")
    )
    private List<Good> goods;

    public void addGood(Good good) {
        if (goods == null) {
            goods = new ArrayList<>();
        }

        goods.add(good);
    }
}
