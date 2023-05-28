package spring.app.marketplace.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "good")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Good {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name shouldn't be empty")
    private String name;

    @Column(name = "price")
    @NotNull(message = "Price shouldn't be empty")
    @Min(value = 0, message = "Price can't be lower than 0")
    private Double price;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "goods")
    private List<Order> orders;

    @ManyToMany(mappedBy = "goods")
    private List<Category> categories;

    @ManyToMany(mappedBy = "goods")
    private List<Bucket> buckets;
}
