package implementations;

import interfaces.Order;

import java.util.Iterator;

public class OrderProcessor implements interfaces.OrderProcessor {

    public OrderProcessor() { }

    @Override
    public int processOrder(Order order) {
        OrderBook OB = (OrderBook) order.getOrderBook();

        boolean partialEdit = false;
        int side = order.getSide();
        Iterator<Order> it_current = OB.getOrderIterator(order.getSide());
        Order orderOnTheBook;

        // While there are orders in the book iterate to fill the order
        while(it_current.hasNext()){
                orderOnTheBook = it_current.next();

                // BUY >= SELL price hence the order can process the order OR BUY <= SELL price hence the order can process the order
                if((order.getPrice() >= orderOnTheBook.getPrice() && side == 1) || (order.getPrice() <= orderOnTheBook.getPrice() && side == 2)){
                    int current_quantity = order.getQuantity();

                    // Current order quantity = on the book order quantity
                    if(order.getQuantity() == orderOnTheBook.getQuantity()) {
                        // All the quantity of passive sell order was sold!
                        orderOnTheBook.setQuantity(0);
                        order.setQuantity(0);
                        it_current.remove();
                        return 2;
                    }
                    // Current order quantity > on the book order quantity
                    else if(order.getQuantity() > orderOnTheBook.getQuantity()){
                        order.setQuantity(current_quantity - orderOnTheBook.getQuantity());
                        orderOnTheBook.setQuantity(0);
                        it_current.remove();
                        partialEdit = true;
                    }
                    // Current order quantity < on the book order quantity
                    else{
                        orderOnTheBook.setQuantity(orderOnTheBook.getQuantity()-order.getQuantity());
                        // Order was fulfilled with passive order
                        order.setQuantity(0);
                        return 2;
                    }
                }

            // Order has been partially filled
            if(partialEdit && !it_current.hasNext())
                return 6;

        }

        // No orders on the book
        return 4;

    }
}
