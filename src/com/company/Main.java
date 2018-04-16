package com.company;

import implementations.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("NASDAQ Matching Engine");

        OrderBook OrderBook = new OrderBook();
        Order Order1 = new Order(OrderBook, 100, 10, 2);
        Order Order2 = new Order(OrderBook, 110, 10, 2);
        Order Order3 = new Order(OrderBook, 120, 10, 2);
        Order Order4 = new Order(OrderBook, 110, 50, 1);
        Order Order5 = new Order(OrderBook, 110, 70, 2);
        Order Order6 = new Order(OrderBook, 300, 10, 2);
        Order Order7 = new Order(OrderBook, 90, 10, 2);
        OrderBook.addOrder(Order1);
        OrderBook.addOrder(Order2);
        OrderBook.addOrder(Order3);
        OrderBook.addOrder(Order4);

   //   OrderBook.addOrder(Order5);
        System.out.println("Debug line!");
   //   OrderBook.addOrder(Order6);
  //    OrderBook.addOrder(Order7);

    }
}
