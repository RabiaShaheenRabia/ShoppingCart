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
public class Item {
    private String itemName;
    private BigDecimal unitPrice;
    private int Quantity;
    private String unitSymbol; //kilo for solid items and Liter for Liquid
}
