package com.bjss.cart.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rabia on 02/12/2017.
 */
public class ItemRules {

    private static String itemName1= "Apples";
    private static String itemName2= "Milk";
    private static String itemName3= "Bread";
    private static String itemName4= "Soup";

    private static Item item1 = new Item(itemName1, BigDecimal.valueOf(1), 1, "Kilo");  //Quantity * unitPrice - (DeductionOnQuantity * percentageDeduction) //1 --->
    private static Item item2 = new Item(itemName2, BigDecimal.valueOf(1.30), 1, "Liter");
    private static Item item3 = new Item(itemName3, BigDecimal.valueOf(0.8), 1, "Kilo");
    private static Item item4 = new Item(itemName4, BigDecimal.valueOf(0.65), 1, "tin");

    private static final Map<String, Item> itemsMap =  new HashMap<>();

    private ItemRules() {
        System.out.println("ItemRules - Constructor");
    }

    static{
        itemsMap.put(itemName1, item1);
        itemsMap.put(itemName2, item2);
        itemsMap.put(itemName3, item3);
        itemsMap.put(itemName4, item4);
    }
    public static final Map<String, Item> getInstance(){
        return itemsMap;
    }
}
