package lk.ijse.gdse.backend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private String code;
    private String description;
    private int qtyOnHand;
    private BigDecimal unitPrice;
}
