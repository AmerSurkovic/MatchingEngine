package implementations;

import interfaces.Order;
import interfaces.OrderProcessor;

import java.util.ArrayList;
import java.util.Iterator;

public class OrderBook implements interfaces.OrderBook {

    private OrderProcessor _orderProcessor;

    private ArrayList<Order> _buyOrders;
    private ArrayList<Order> _sellOrders;

    // Passive BUY order with highest price will be always the first element
    private void addBuyOrder(Order order){
        boolean empty = false;

        if(_buyOrders.size() == 0){
            _buyOrders.add(order);
            empty = true;
        }

        /**
         * We add orders into the _buyOrders list so that it is sorted by price from highest to lowest order.
         * This is so that the order processor matching sell order to the passive buy orders has immediate access to the order with highest price.
         * For big data, binary search could be implemented to speed up the addBuyOrder!
         */
        for(int i=0; i<_buyOrders.size(); i++){
            if(empty)
                break;
            else if(order.getPrice() >= _buyOrders.get(i).getPrice()) {
                _buyOrders.add(i, order);
                break;
            }
            else if(i == _buyOrders.size()-1){
                _buyOrders.add(order);
                break;
            }
        }
    }

    // Passive SELL order with lowest price will be always the first element
    private void addSellOrder(Order order){
        boolean empty = false;

        if(_sellOrders.size() == 0){
            _sellOrders.add(order);
            empty = true;
        }

        /**
         * We add orders into the _sellOrders list so that it is sorted by price from lowest to highest order.
         * This is so that the order processor matching buy order to the passive sell orders has immediate access to the order with lowest price.
         * For big data, binary search could be implemented to speed up the addSellOrder!
         */
        for(int i=0; i<_sellOrders.size(); i++){
            if(empty)
                break;
            else if(order.getPrice() <= _sellOrders.get(i).getPrice()){
                _sellOrders.add(i, order);
                break;
            }
            else if(i == _sellOrders.size()-1){
                _sellOrders.add(order);
                break;
            }
        }
    }

    public OrderBook() {
        _orderProcessor = new implementations.OrderProcessor();
        _buyOrders = new ArrayList<>();
        _sellOrders = new ArrayList<>();
    }

    @Override
    public void addOrder(Order order) {
        int statusCode = _orderProcessor.processOrder(order);
        int side = order.getSide();

        if(statusCode == 4 || statusCode == 6){
            if(side == 1)
                addBuyOrder(order);
            else
                addSellOrder(order);
        }

        System.out.println(statusCode);
        //System.out.println("BUY " + _buyOrders.size());
        //System.out.println("SELL " + _sellOrders.size());
    }

    @Override
    public Iterator<Order> getOrderIterator(int side) {
        if(side == 1)
            // For the BUY order, sell orders list is viewed for comparison
            return _sellOrders.iterator();
        else
            // For the SELL order, buy orders list is viewed for comparison
            return _buyOrders.iterator();
    }

    @Override
    public OrderProcessor getOrderProcessor() {
        return _orderProcessor;
    }

    @Override
    public void setOrderProcessor(OrderProcessor orderProcessor) {
        _orderProcessor = orderProcessor;
    }
}
