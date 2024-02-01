package lk.ijse.gdse.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

    private String oId;
    private String code;
    private BigDecimal price;
    private int qty;


}
