package lk.ijse.gdse.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {


    private String code;
    private String description;
    private int qtyOnHand;
    private BigDecimal unitPrice;
}
