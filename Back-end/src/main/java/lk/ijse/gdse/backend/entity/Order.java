package lk.ijse.gdse.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String oId;
    private LocalDate date;
    private String customerId;
}
