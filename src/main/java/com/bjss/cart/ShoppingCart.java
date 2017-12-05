package com.bjss.cart;


import com.bjss.cart.process.ScanShoppingItems;

import java.math.BigDecimal;
import java.util.*;

import static com.bjss.cart.process.ScanShoppingItems.tokenizeInput;

public class ShoppingCart {


    public static ScanShoppingItems scanShoppingItems;
    public static void main(String[] args) {

        String str = Arrays.toString(args).replaceAll(","," ").replace("[","").replace("]","");
        //str = "PriceBasket Apples Milk Bread";
        if(str !=null && !str.isEmpty() && str.contains("PriceBasket")) {
            String[] tokens = tokenizeInput(str);
            scanShoppingItems.initBasket(tokens);
            scanShoppingItems.computePrice();
            BigDecimal AfterDiscount = scanShoppingItems.computePriceOnItemsAfterDisountInBasketOnOffer(tokens);
            BigDecimal itemsWithoutDiscount = scanShoppingItems.computePriceOnItemsNotOnOffer(tokens);
            System.out.println("SubTotal : " + scanShoppingItems.basket.getPrice());
            System.out.println("All Items On Offer : " + scanShoppingItems.computeItemsOnOffer(tokens) + "  OFF " + AfterDiscount);
            System.out.println("Total : " + scanShoppingItems.basket.getPrice().subtract(AfterDiscount));
            System.out.println("Items Not on Offer : " + scanShoppingItems.computeItemsNotOnOffer(tokens) + " SubTotal  " +
                    scanShoppingItems.computePriceOnItemsNotOnOffer(tokens));
        }
        else
            System.exit(-1);
    }



}