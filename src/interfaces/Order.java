package interfaces;

public interface Order {

    /**
     * The order book that this order is for
     * @return the order book
     */
    OrderBook getOrderBook();

    /**
     * Set the order book that this order is for
     * @param orderBook the order book to set
     */
    void setOrderBook(OrderBook orderBook);

    /**
     * The price for this order
     * @return the price
     */
    int getPrice();

    /**
     * Set the price for this order
     * @param price the price
     */
    void setPrice(int price);

    /**
     * The quantity for this order
     * @return the quantity
     */
    int getQuantity();

    /**
     * Set the quantity for this order
     * @param quantity the quantity
     */
    void setQuantity(int quantity);

    /**
     * The side of this order
     * @return the side (1=Buy, 2=Sell)
     */
    int getSide();

    /**
     * Set the side of this order
     * @param side the side (1=Buy, 2=Sell)
     */
    void setSide(int side);
}
