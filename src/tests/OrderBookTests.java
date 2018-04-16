package tests;

import implementations.Order;
import implementations.OrderBook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class OrderBookTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final OrderBook OrderBook = new OrderBook();

    @Before
    public void setUpStream(){
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStream(){
        System.setOut(System.out);
    }

    @Test
    public void NoSellOrderExists_Assert_4(){
        Order Order1 = new Order(OrderBook, 100, 10, 1);
        OrderBook.addOrder(Order1);

        assertEquals("4", outContent.toString().replaceAll("\\s",""));
    }

    @Test
    public void NoBuyOrderExists_Assert_4(){
        Order Order1 = new Order(OrderBook, 100, 10, 2);
        OrderBook.addOrder(Order1);

        assertEquals("4", outContent.toString().replaceAll("\\s",""));
    }

    @Test
    public void SellOrderMatchesPassiveBuyOrder_Assert_2(){
        Order Order1 = new Order(OrderBook, 100,10,1);
        Order Order2 = new Order(OrderBook, 100,10,2);

        OrderBook.addOrder(Order1);
        // Status code 4
        OrderBook.addOrder(Order2);
        // Status code 2

        assertEquals("42", outContent.toString().replaceAll("\\s",""));
    }

    @Test
    public void BuyOrderMatchesPassiveSellOrder_Assert_2(){
        Order Order1 = new Order(OrderBook, 100,10,2);
        Order Order2 = new Order(OrderBook, 100,10,1);

        OrderBook.addOrder(Order1);
        // Status code 4
        OrderBook.addOrder(Order2);
        // Status code 2

        assertEquals("42", outContent.toString().replaceAll("\\s",""));
    }

    @Test
    public void BuyOrderPartiallyFilled_PlacedOnBook_Assert_6(){
        Order Order1 = new Order(OrderBook, 100, 10, 2);
        Order Order2 = new Order(OrderBook, 110, 10, 2);
        Order Order3 = new Order(OrderBook, 120, 10, 2);
        Order Order4 = new Order(OrderBook, 110, 50, 1);

        OrderBook.addOrder(Order1);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order2);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order3);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order4);
        /**
         * Order matched with Order1 (Order1.price < Order4.price) - Order1 removed from the book
         * Order matched with order2 (Order2.price == Order4.price - Order2 removed from the book
         * Order not matched with Order3 for the price is higher then wanted.
         * Order is placed on the book with remaining quantity of 30.
          */

        assertEquals("4446", outContent.toString().replaceAll("\\s",""));
        assertEquals(30, Order4.getQuantity());
    }

    @Test
    public void SellOrderPartiallyFilled_PlacedOnBook_Assert_6(){
        Order Order1 = new Order(OrderBook, 100, 10, 1);
        Order Order2 = new Order(OrderBook, 110, 10, 1);
        Order Order3 = new Order(OrderBook, 120, 10, 1);
        Order Order4 = new Order(OrderBook, 110, 50, 2);

        OrderBook.addOrder(Order1);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order2);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order3);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order4);
        /**
         * Order matched with Order1 (Order1.price < Order4.price) - Order1 removed from the book
         * Order matched with order2 (Order2.price == Order4.price - Order2 removed from the book
         * Order not matched with Order3 for the price is higher then wanted.
         * Order is placed on the book with remaining quantity of 30.
         */

        assertEquals("4446", outContent.toString().replaceAll("\\s",""));
        assertEquals(30, Order4.getQuantity());
    }

    @Test
    public void IncomingBuyOrder_Matches_MostAttractiveSellOrder(){
        Order Order1 = new Order(OrderBook, 300, 10, 2);
        Order Order2 = new Order(OrderBook, 100, 10, 2);
        Order Order3 = new Order(OrderBook, 600, 10, 2);
        Order Order4 = new Order(OrderBook, 1000, 10, 1);

        OrderBook.addOrder(Order1);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order2);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order3);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order4);

        assertEquals("4442", outContent.toString().replaceAll("\\s",""));
        assertEquals(0, Order2.getQuantity());
        assertEquals(0, Order4.getQuantity());
        assertEquals(10, Order1.getQuantity());
        assertEquals(10, Order3.getQuantity());
    }

    @Test
    public void IncomingSellOrder_Matches_MostAttractiveBuyOrder(){
        Order Order1 = new Order(OrderBook, 300, 10, 1);
        Order Order2 = new Order(OrderBook, 100, 10, 1);
        Order Order3 = new Order(OrderBook, 600, 10, 1);
        Order Order4 = new Order(OrderBook, 50, 10, 2);

        OrderBook.addOrder(Order1);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order2);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order3);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order4);

        assertEquals("4442", outContent.toString().replaceAll("\\s",""));
        assertEquals(0, Order3.getQuantity());
        assertEquals(0, Order4.getQuantity());
        assertEquals(10, Order1.getQuantity());
        assertEquals(10, Order2.getQuantity());
    }

    @Test
    public void IncomingBuyOrder_Matches_MultipleSellOrders(){
        Order Order1 = new Order(OrderBook, 100, 10, 2);
        Order Order2 = new Order(OrderBook, 110, 10, 2);
        Order Order3 = new Order(OrderBook, 120, 10, 2);
        Order Order4 = new Order(OrderBook, 120, 30, 1);

        OrderBook.addOrder(Order1);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order2);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order3);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order4);

        assertEquals("4442", outContent.toString().replaceAll("\\s",""));
    }

    @Test
    public void IncomingSellOrder_Matches_MultipleBuyOrders(){
        Order Order1 = new Order(OrderBook, 100, 10, 1);
        Order Order2 = new Order(OrderBook, 110, 10, 1);
        Order Order3 = new Order(OrderBook, 120, 10, 1);
        Order Order4 = new Order(OrderBook, 100, 30, 2);

        OrderBook.addOrder(Order1);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order2);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order3);
        // Order placed on the book - 4 returned
        OrderBook.addOrder(Order4);

        assertEquals("4442", outContent.toString().replaceAll("\\s",""));
    }
}
