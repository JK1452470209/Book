package com.service;

import com.pojo.Cart;
import com.pojo.Order;
import com.pojo.OrderItem;

import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-04-09  15:30
 */
public interface OrderService {
    public String createOrder(Cart cart,Integer userId);

    public List<Order> myOrders(Integer userId);

    public OrderItem orderDetails(String orderId);

    public List<Order> allOrders();

    //发货
    public void sendOrder(String orderId);

    //收货
    public void reveiveOrder(String orderId);



}
