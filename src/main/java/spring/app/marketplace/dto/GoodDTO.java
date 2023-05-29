package spring.app.marketplace.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GoodDTO {

    @NotEmpty(message = "Name shouldn't be empty")
    private String name;

    @NotNull(message = "Price shouldn't be empty")
    @Min(value = 0, message = "Price can't be lower than 0")
    private Double price;

    private String description;

    private List<CategoryDTO> categories;
}
