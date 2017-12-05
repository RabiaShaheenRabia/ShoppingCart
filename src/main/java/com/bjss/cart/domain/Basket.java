package com.bjss.cart.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rabia on 02/12/2017.
 */
@Builder
@AllArgsConstructor
@Data
public class Basket {
    private BigDecimal price;
    private List<Item> items;

    public Basket(){
        items = new ArrayList<Item>();
    }
}
