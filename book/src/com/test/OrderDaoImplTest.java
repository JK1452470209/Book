package com.test;

import com.dao.OrderDao;
import com.dao.impl.OrderDaoImpl;
import com.pojo.Order;
import com.utils.JdbcUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.Assert.*;

/**
 * @outhor Mr.JK
 * @create 2020-04-09  15:18
 */
public class OrderDaoImplTest {
    OrderDao orderDao = new OrderDaoImpl();

    @Test
    public void saveOrder() {

        orderDao.saveOrder(new Order("123456789",new Date(),new BigDecimal(100),0,1));
    }

    @Test
    public void queryMyOrders(){
        List<Order> orders = orderDao.queryMyOrders(1);
        for (Order order : orders) {
            System.out.println(order);
        }

    }

    @Test
    public void allOrders() {
        List<Order> orders = orderDao.allOrders();
        System.out.println(orders);
    }

    @Test
    public void changOrderStatus() {
        orderDao.changOrderStatus("15866037542541");
        // 默认设置为不自动提交事务
        JdbcUtils.commitAndClose();
    }
}