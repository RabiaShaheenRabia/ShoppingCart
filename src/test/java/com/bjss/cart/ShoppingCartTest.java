package com.bjss.cart;

import com.bjss.cart.domain.Basket;
import com.bjss.cart.domain.Item;
import com.bjss.cart.process.ScanShoppingItems;
import org.junit.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertSame;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Rabia on 02/12/2017.
 */
public class ShoppingCartTest {

    final static Item item1=new Item("Apples",BigDecimal.valueOf(1),1,"Kilo");  //Quantity * unitPrice - (DeductionOnQuantity * percentageDeduction) //1 --->
    final static Item item2=new Item("Milk",BigDecimal.valueOf(1.30),1,"Liter");
    final static Item item3=new Item("Bread",BigDecimal.valueOf(0.8),1,"Kilo");
    final static Item item4=new Item("Soup",BigDecimal.valueOf(0.65),1,"tin");

    static Map<String, Item> items = new HashMap<String, Item>();

    @BeforeClass
    public static void runOnceBeforeClass() {
        System.out.println("@BeforeClass - runOnceBeforeClass");
        items.put(item1.getItemName(),item1);
        items.put(item2.getItemName(),item2);
        items.put(item3.getItemName(),item3);
        items.put(item4.getItemName(),item4);
    }

    // Run once, e.g close connection, cleanup
    @AfterClass
    public static void runOnceAfterClass() {
        System.out.println("@AfterClass - runOnceAfterClass");
    }

    @Before
    public void runBeforeTestMethod() {
        System.out.println("@Before - runBeforeTestMethod");
        ScanShoppingItems.setPriceAfterDiscount(BigDecimal.valueOf(0));
        ScanShoppingItems.setPriceNotOnOffer(BigDecimal.valueOf(0));
    }

    @After
    public void runAfterTestMethod() {
        System.out.println("@After - runAfterTestMethod");
        ScanShoppingItems.setPriceAfterDiscount(BigDecimal.valueOf(0));
        ScanShoppingItems.setPriceNotOnOffer(BigDecimal.valueOf(0));

    }

    int expectedTokenSize = 4;

    @Test
    public void TestBasketContent(){
        String str = "PriceBasket Apples Milk Bread";
        String[] tokenizer = ScanShoppingItems.tokenizeInput(str);
        assertEquals(expectedTokenSize,tokenizer.length);
        assertEquals("Apples",tokenizer[1]);
        assertEquals("Milk",tokenizer[2]);
        assertEquals("Bread",tokenizer[3]);
    }

    int expectedBasketSize = 3;
    @Test
    public void TestBasketContents(){
        String str = "PriceBasket Apples Milk Bread";
        String[] tokens = str.split(" ");
        Basket basket = ScanShoppingItems.initBasket(tokens);
        assertSame(expectedBasketSize,basket.getItems().size());
    }

    BigDecimal expectedContentPrice = BigDecimal.valueOf(3.1);
    @Test
    public void TestBasketContentPrice(){
        String str = "PriceBasket Apples Milk Bread";
        String[] tokens = str.split(" ");
        ScanShoppingItems.initBasket(tokens);
        ScanShoppingItems.computePrice();
        assertEquals(expectedContentPrice,ScanShoppingItems.basket.getPrice());
    }




    @Test
    public void TestComputeNumberOfTimesEachItemInBasket(){
        String str = "PriceBasket Apples Milk Bread Soup Soup";
        String[] tokens = str.split(" ");
        Map<String,Integer> itemsCount = ScanShoppingItems.computeNumberOfTimesEachItemInBasket(tokens);
        assertEquals(Integer.valueOf(1),itemsCount.get("Apples"));
        assertEquals(Integer.valueOf(1),itemsCount.get("Milk"));
        assertEquals(Integer.valueOf(1),itemsCount.get("Bread"));
        assertEquals(Integer.valueOf(2),itemsCount.get("Soup"));
    }

    @Test
    public void TestComputeItemsOnOffer(){
        String str = "PriceBasket Apples Milk Bread";
        String[] tokens = str.split(" ");
        Set<String> itemsOnOffer = ScanShoppingItems.computeItemsOnOffer(tokens);
        assertEquals(Integer.valueOf(2),Integer.valueOf(itemsOnOffer.size()));
    }
    @Test
    public void TestComputeItemsNotOnOffer(){
        String str = "PriceBasket Apples Milk Bread";
        String[] tokens = str.split(" ");
        Set<String> itemsOnOffer = ScanShoppingItems.computeItemsNotOnOffer(tokens);
        assertEquals(Integer.valueOf(1),Integer.valueOf(itemsOnOffer.size()));
    }

    @Test
    public void TestComputePriceOnItemsAfterDisountInBasketOnOfferForSingleEntity(){
        String str = "PriceBasket Apples Milk  Bread ";
        String[] tokens = str.split(" ");
        BigDecimal price = ScanShoppingItems.computePriceOnItemsAfterDisountInBasketOnOffer(tokens);
        assertEquals(BigDecimal.valueOf(0.1),price);
    }
    @Test
    public void TestComputePriceOnItemsAfterDisountInBasketOnOfferForMultipleEntity(){
        String str = "PriceBasket Soup Bread Soup";
        String[] tokens = str.split(" ");
        BigDecimal price = ScanShoppingItems.computePriceOnItemsAfterDisountInBasketOnOffer(tokens);
        ScanShoppingItems.initBasket(tokens);
        BigDecimal totalPrice = ScanShoppingItems.computePrice();
        assertEquals(new String("1.050"),String.valueOf(totalPrice.subtract(price)));
    }

    @Test
    public void TestComputePriceOnItemsAfterDisountInBasketOnOfferForMultipleEntityWithApple(){
        String str = "PriceBasket Soup Bread Soup Apples";
        String[] tokens = str.split(" ");
        BigDecimal priceOnOffer = ScanShoppingItems.computePriceOnItemsAfterDisountInBasketOnOffer(tokens);
        ScanShoppingItems.initBasket(tokens);
        BigDecimal price = ScanShoppingItems.computePrice();
        assertEquals(String.valueOf("1.950"),String.valueOf(price.subtract(priceOnOffer)));
    }


    @Test
    public void TestComputePriceOnItemsAfterDisountInBasketOnOfferForMultipleEntityWith1MoreBread(){
        String str = "PriceBasket Soup Bread Soup Bread";
        String[] tokens = str.split(" ");

        BigDecimal priceOnOffer = ScanShoppingItems.computePriceOnItemsAfterDisountInBasketOnOffer(tokens);
        ScanShoppingItems.initBasket(tokens);
        BigDecimal price = ScanShoppingItems.computePrice();
        assertEquals(String.valueOf("1.850"),String.valueOf(price.subtract(priceOnOffer)));

    }

    @Test
    public void TestComputePriceOnItemsAfterDisountInBasketOnOfferForMultipleEntityWith2MoreSoup(){
        String str = "PriceBasket Soup Bread Soup Soup Soup";
        String[] tokens = str.split(" ");
        BigDecimal priceOnOffer = ScanShoppingItems.computePriceOnItemsAfterDisountInBasketOnOffer(tokens);
        ScanShoppingItems.initBasket(tokens);
        BigDecimal price = ScanShoppingItems.computePrice();
        assertEquals(String.valueOf("2.350"),String.valueOf(price.subtract(priceOnOffer)));

    }


    @Test
    public void TestBasketContentPriceAfterSpecialOfferRule(){
        String str = "PriceBasket Apples Milk Bread";
        String[] tokens = str.split(" ");
        ScanShoppingItems.initBasket(tokens);
        BigDecimal totalPrice = ScanShoppingItems.computePrice();
        BigDecimal price = ScanShoppingItems.computePriceOnItemsAfterDisountInBasketOnOffer(tokens);
        assertEquals(new String("3.0"),String.valueOf(totalPrice.subtract(price)));
    }

}
