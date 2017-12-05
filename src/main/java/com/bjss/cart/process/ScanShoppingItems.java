package com.bjss.cart.process;

import com.bjss.cart.ShoppingCart;
import com.bjss.cart.domain.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Rabia on 03/12/2017.
 */
public class ScanShoppingItems {

    public static Basket basket = new Basket();

    public static String[] tokenizeInput(String str) {
        if (!str.isEmpty() && str != null)
            return str.split(" ");
        return null;
    }

    public static Basket initBasket(String[] tokens) {
        List<Item> items = new ArrayList<Item>();
        System.out.println(ItemRules.getInstance().keySet().size());
        for (String str : tokens) {
            if (ItemRules.getInstance().keySet().contains(str)) {
                Item item = ItemRules.getInstance().get(str);
                items.add(item);
            }
        }
        ScanShoppingItems.basket.setItems(items);
        return ScanShoppingItems.basket;
    }

    public static BigDecimal computePrice() {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (Item item : ScanShoppingItems.basket.getItems()) {
            BigDecimal iQuantity = new BigDecimal(item.getQuantity());
            totalPrice = totalPrice.add(item.getUnitPrice().multiply(iQuantity));
        }
        ScanShoppingItems.basket.setPrice(totalPrice);
        return totalPrice;
    }

    public static Map<String, Integer> computeNumberOfTimesEachItemInBasket(String[] tokens) {
        Map<String, Integer> itemCounts = new HashMap<>();
        int count = 1;
        for (String str : tokens) {
            if (ItemRules.getInstance().containsKey(str))
                if (itemCounts.containsKey(str)) {
                    count = itemCounts.get(str);
                    itemCounts.put(str, itemCounts.get(str) + 1);
                } else {
                    itemCounts.put(str, count++);
                    count = 1;
                }
        }
        return itemCounts;
    }

    public static Set<String> computeItemsOnOffer(String[] tokens) {
        Map<Integer, List<SpecialDiscount>> specialOffers = SpecialOfferRules.getInstance();
        Set<String> itemsOnOffer = new HashSet<String>();
        Map<String, Integer> itemsInBasket = computeNumberOfTimesEachItemInBasket(tokens);
        for (int i = 1; i <= specialOffers.size(); i++) {
            List<SpecialDiscount> offers = specialOffers.get(i);
            for (SpecialDiscount sd : offers) {
                if (!itemsOnOffer.contains(sd.getItemName()) && itemsInBasket.containsKey(sd.getItemName()))
                    itemsOnOffer.add(sd.getItemName());
            }
        }

        return itemsOnOffer;
    }

    public static Set<String> computeItemsNotOnOffer(String[] tokens) {

        Set<String> itemsOnOffer = computeItemsOnOffer(tokens);
        Map<String, Integer> itemsInBasket = computeNumberOfTimesEachItemInBasket(tokens);
        Set<String> itemsNotOnOffer = new HashSet<String>();
        for (String str : itemsInBasket.keySet()) {
            if (!itemsOnOffer.contains(str)) {
                itemsNotOnOffer.add(str);
            }
        }

        return itemsNotOnOffer;
    }

    @Setter
    static BigDecimal priceAfterDiscount = BigDecimal.valueOf(0);
    @Setter
    static BigDecimal priceNotOnOffer = BigDecimal.valueOf(0);

    public static BigDecimal computePriceOnItemsAfterDisountInBasketOnOffer(String[] tokens)
    {

        Map<Integer, List<SpecialDiscount>> specialOffers = SpecialOfferRules.getInstance();
        Map<String, Integer> itemsInBasket = computeNumberOfTimesEachItemInBasket(tokens);

        BigDecimal actulPrice = BigDecimal.valueOf(0);
        for (int i = 1; i <= specialOffers.size(); i++) {
            List<SpecialDiscount> offers = specialOffers.get(i);
            if (offers.size() == 1) {
                for (SpecialDiscount sd : offers) {
                    Item item = ItemRules.getInstance().get(sd.getItemName());
                    if (itemsInBasket.containsKey(sd.getItemName())) {
                        priceAfterDiscount = priceAfterDiscount.add(
                                sd.getPercentageDeduction().
                                        multiply(item.getUnitPrice()
                                        ));

                        //actulPrice = BigDecimal.valueOf(item.getQuantity()).multiply(
                          //      item.getUnitPrice()
                        //);

//                        priceAfterDiscount = actulPrice.subtract(priceAfterDiscount);
                    }
                }

            } else {

                System.out.println(itemsInBasket.keySet().toString());
                System.out.println(offers.toString());
                if (itemsInBasket.containsKey("Soup") && itemsInBasket.containsKey("Bread")) {
                    int soupCount = itemsInBasket.get("Soup");
                    int breadCount = itemsInBasket.get("Bread");
                    if (soupCount > 1 && breadCount > 0) {
                        while (soupCount >= 1 && breadCount > 0) {
                            List<SpecialDiscount> sds = SpecialOfferRules.getInstance().get(2);
                            for (SpecialDiscount sd : sds) {
                                Item item = ItemRules.getInstance().get(sd.getItemName());
                                priceAfterDiscount = priceAfterDiscount.add(
                                        sd.getPercentageDeduction().
                                                multiply(item.getUnitPrice()).multiply(
                                                BigDecimal.valueOf(sd.getDeductionOnQuantity())
                                        ));

                            }
                            soupCount = soupCount - 2;
                            breadCount = breadCount - 1;
                        }

                    }
                    if ((soupCount >= 1)) {
                        //Normal Price for remaining items

                        Item item = ItemRules.getInstance().get("Soup");
                        priceNotOnOffer = priceNotOnOffer.add(
                                BigDecimal.valueOf(soupCount).
                                        multiply(item.getUnitPrice())
                        );

                    }
                    if ((breadCount >= 1)) {
                        //Normal Price for remaining items

                        Item item = ItemRules.getInstance().get("Bread");
                        priceNotOnOffer = priceNotOnOffer.add(
                                BigDecimal.valueOf(breadCount).
                                        multiply(item.getUnitPrice())
                        );

                    }
                }}
        }
        return priceAfterDiscount;
    }
    public static BigDecimal computePriceOnItemsNotOnOffer(String[] tokens) {

        Set<String> items =  computeItemsNotOnOffer(tokens);
        Map<String, Integer> itemsInBasket = computeNumberOfTimesEachItemInBasket(tokens);
        Item item = new Item();
        BigDecimal price = BigDecimal.valueOf(0);
        for (String str:itemsInBasket.keySet())
        {
            if (items.contains(str) && ItemRules.getInstance().containsKey(str)) {
                item = ItemRules.getInstance().get(str);
                price = price.add(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }

        return price;
    }
}
