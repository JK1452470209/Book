package com.dao;

import com.pojo.Order;

import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-04-09  15:08
 */
public interface OrderDao {
    //创建订单
    public int saveOrder(Order order);

    public List<Order> queryMyOrders(Integer userId);

    public List<Order> allOrders();

    public void changOrderStatus(String orderId);
}
