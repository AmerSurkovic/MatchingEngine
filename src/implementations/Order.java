package implementations;

import interfaces.OrderBook;

public class Order implements interfaces.Order {

    private OrderBook _orderBook;
    private int _price;
    private int _quantity;
    private int _side;

    public Order(OrderBook orderBook, int price, int quantity, int side) {

        if(price <= 0)
            throw new IllegalArgumentException("Price cannot be negative.");
        else if(quantity <= 0)
            throw new IllegalArgumentException("Quantity cannot be negative.");
        else if(side < 1 || side > 2)
            throw new IllegalArgumentException("Side must be either 1 (BUY) or 2 (SELL)");

        _orderBook = orderBook;
        _price = price;
        _quantity = quantity;
        _side = side;
    }

    @Override
    public OrderBook getOrderBook() {
        return _orderBook;
    }

    @Override
    public void setOrderBook(OrderBook orderBook) {
        _orderBook = orderBook;
    }

    @Override
    public int getPrice() {
        return _price;
    }

    @Override
    public void setPrice(int price) {
        if(price < 0)
            throw new IllegalArgumentException("Price cannot be negative.");

        _price = price;
    }

    @Override
    public int getQuantity() {
        return _quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        if(quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be negative.");

        _quantity = quantity;
    }

    @Override
    public int getSide() {
        return _side;
    }

    @Override
    public void setSide(int side) {
        if(side < 1 || side > 2)
            throw new IllegalArgumentException("Side must be either 1 (BUY) or 2 (SELL)");

        _side = side;
    }
}
