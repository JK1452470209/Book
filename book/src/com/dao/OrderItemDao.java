package com.dao;

import com.pojo.OrderItem;

import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-04-09  15:09
 */
public interface OrderItemDao {

    public int saveOrderItem(OrderItem orderItem);

    public OrderItem queryOrderDetailByOrderId(String orderId);


}
