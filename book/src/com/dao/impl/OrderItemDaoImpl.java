package com.dao.impl;

import com.dao.OrderItemDao;
import com.pojo.OrderItem;

import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-04-09  15:15
 */
public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "insert into t_order_item(`name`,`count`,`price`,`total_price`,`order_id`)values(?,?,?,?,?)";
        return update(sql,orderItem.getName(),orderItem.getCount(),orderItem.getPrice(),orderItem.getTotalPrice(),orderItem.getOrderId());
    }

    @Override
    public OrderItem queryOrderDetailByOrderId(String orderId) {
        String sql = "SELECT `name` , `count` , `price` , `total_price` as totalPrice, `order_id` as orderId  FROM t_order_item WHERE order_id = ?";
        return queryForOne(OrderItem.class,sql,orderId);
    }


}
