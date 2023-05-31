package spring.app.marketplace.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import spring.app.marketplace.models.Bucket;
import spring.app.marketplace.models.Order;
import spring.app.marketplace.util.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PersonDTO {

    @NotEmpty(message = "Email shouldn't be empty")
    @Email
    private String email;

    @NotNull(message = "Role shouldn't be empty")
    private Role role;

    private List<OrderDTO> orders;

    private BucketDTO bucket;
}
