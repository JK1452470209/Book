package com.test;

import com.pojo.Cart;
import com.pojo.CartItem;
import com.pojo.Order;
import com.pojo.OrderItem;
import com.service.OrderService;
import com.service.impl.OrderServiceImpl;
import com.utils.JdbcUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @outhor Mr.JK
 * @create 2020-04-09  15:43
 */
public class OrderServiceImplTest {

    @Test
    public void createOrder() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"java",1,new BigDecimal(100),new BigDecimal(100)));
        cart.addItem(new CartItem(1,"java",1,new BigDecimal(100),new BigDecimal(100)));
        cart.addItem(new CartItem(2,"数据结构",2,new BigDecimal(100),new BigDecimal(200)));

        OrderService orderService = new OrderServiceImpl();
        System.out.println("订单号为：" + orderService.createOrder(cart, 1));
    }


    @Test
    public void myOrders() {
        OrderService orderService = new OrderServiceImpl();
        List<Order> orders = orderService.myOrders(1);
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    @Test
    public void orderDetails() {
        OrderService orderService = new OrderServiceImpl();
        OrderItem orderItem = orderService.orderDetails("15864228412151");
        System.out.println(orderItem);
//        return orderItemDao.queryOrderDetailByOrderId(orderId);
    }

    @Test
    public void allOrders() {
        OrderService orderService = new OrderServiceImpl();
        List<Order> orders = orderService.allOrders();
        System.out.println(orders);
    }


    @Test
    public void sendOrder() {
        OrderService orderService = new OrderServiceImpl();
        orderService.sendOrder("15866037542541");
        JdbcUtils.commitAndClose();
    }

    @Test
    public void reveiveOrder() {
        OrderService orderService = new OrderServiceImpl();
        orderService.reveiveOrder("15866037542541");
        JdbcUtils.commitAndClose();
    }


}