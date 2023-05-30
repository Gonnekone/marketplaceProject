package spring.app.marketplace.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import spring.app.marketplace.util.Status;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderDTO {

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Date of creation shouldn't be empty")
    private LocalDateTime created_at;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime changed_at;

    @NotNull(message = "Status shouldn't be empty")
    private Status status;

    private List<GoodDTO> goods;
}
