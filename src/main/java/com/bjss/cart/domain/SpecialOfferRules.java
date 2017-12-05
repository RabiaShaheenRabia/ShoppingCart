package com.bjss.cart.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rabia on 02/12/2017.
 */
public class SpecialOfferRules {


    private static final Map<Integer,List<SpecialDiscount>> specialOffers = new HashMap<Integer,List<SpecialDiscount>>();

    private static String itemName1= "Apples";
    private static String itemName2= "Milk";
    private static String itemName3= "Bread";
    private static String itemName4= "Soup";

    private static int offersCount =2;

    private static SpecialDiscount sd1 = new SpecialDiscount(itemName1, 1,BigDecimal.valueOf(0.1));  //Quantity * unitPrice - (DeductionOnQuantity * percentageDeduction) //1 --->
    private static SpecialDiscount sd2 = new SpecialDiscount(itemName3,1,BigDecimal.valueOf(0.5));
    private static SpecialDiscount sd3 = new SpecialDiscount(itemName4,2,BigDecimal.valueOf(0.5));


    private static List<SpecialDiscount> offers =  new ArrayList<>();

    private SpecialOfferRules() {
        System.out.println("SpecialOfferRules - Constructor");
    }

    static{
        offers.add( sd1);
        specialOffers.put(1,offers);

        offers = new ArrayList<>();
        offers.add(sd2);
        offers.add(sd3);

        specialOffers.put(2,offers);
    }
    public static final Map<Integer,List<SpecialDiscount>>  getInstance(){
        return specialOffers;
    }

}
