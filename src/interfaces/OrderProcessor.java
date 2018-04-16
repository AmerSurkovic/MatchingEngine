package interfaces;

public interface OrderProcessor {

    /**
     * Process the specified order and return the order status
     * @param order the order to process
     * @return the order status code
     */
    int processOrder(Order order);
}
