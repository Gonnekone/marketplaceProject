package spring.app.marketplace.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "good")
@Data
@Builder
@AllArgsConstructor
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
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Category> categories;

    @ManyToMany(mappedBy = "goods")
    private List<Bucket> buckets;

    public void addCategory(Category category) {
        if (categories == null) {
            categories = new ArrayList<>();
        }

        categories.add(category);
    }

    public void addBucket(Bucket bucket) {
        if (buckets == null) {
            buckets = new ArrayList<>();
        }

        buckets.add(bucket);
    }
}
