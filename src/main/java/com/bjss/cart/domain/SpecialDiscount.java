package com.bjss.cart.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by Rabia on 02/12/2017.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecialDiscount {
    private String itemName;
    private int DeductionOnQuantity;
    private BigDecimal percentageDeduction;
}
