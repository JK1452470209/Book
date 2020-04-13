package com.dao.impl;

import com.dao.OrderDao;
import com.pojo.Order;

import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-04-09  15:10
 */
public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public int saveOrder(Order order) {
        String sql = "insert into t_order(`order_id`,`create_time`,`price`,`status`,`user_id`)values(?,?,?,?,?)";
        return update(sql, order.getOrderId(), order.getCreateTime(), order.getPrice(), order.getStatus(), order.getUserId());

    }

    @Override
    public List<Order> queryMyOrders(Integer userId) {
        String sql = "SELECT `order_id` as orderId , `create_time` as createTime , `price` , `status` , `user_id` as userId  FROM t_order WHERE user_id = ?";
        return queryForList(Order.class,sql,userId);
    }

    @Override
    public List<Order> allOrders() {
        String sql = "SELECT `order_id` as orderId , `create_time` as createTime , `price` , `status` , `user_id` as userId  FROM t_order";
        return queryForList(Order.class,sql);
    }

    @Override
    public void changOrderStatus(String orderId) {
        String sql = "update t_order SET status=(CASE WHEN status = 0 THEN  1 WHEN status = 1 THEN  2 END) WHERE order_id =?";
        int update = update(sql, orderId);
        //System.out.println(update);
    }
}
