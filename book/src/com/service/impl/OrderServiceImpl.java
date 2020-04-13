package com.service.impl;

import com.dao.BookDao;
import com.dao.OrderDao;
import com.dao.OrderItemDao;
import com.dao.impl.BookDaoImpl;
import com.dao.impl.OrderDaoImpl;
import com.dao.impl.OrderItemDaoImpl;
import com.pojo.*;
import com.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @outhor Mr.JK
 * @create 2020-04-09  15:31
 */
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {
        //订单号==唯一性
        String orderId = System.currentTimeMillis() + "" + userId;
        //创建一个订单对象
        Order order = new Order(orderId,new Date(),cart.getTotalPrice(),0,userId);

        //保存订单
        orderDao.saveOrder(order);

        //制造错误500
        //int i = 12 / 0;

        //遍历购物车中每一个商品项转换称为订单项保存到数据库
        for (Map.Entry<Integer, CartItem>entry:cart.getItems().entrySet()){
            //获取每一个购物车中的商品项
            CartItem cartItem = entry.getValue();
            //转换为每一个订单项
            OrderItem orderItem = new OrderItem(null,cartItem.getName(),cartItem.getCount(),cartItem.getPrice(),cartItem.getTotalPrice(),orderId);
            //保存订单项到数据库
            orderItemDao.saveOrderItem(orderItem);

            //更新库存和销量
            Book book = bookDao.queryBookById(cartItem.getId());
            book.setSales(book.getSales() + cartItem.getCount());
            book.setStock(book.getStock() - cartItem.getCount());
            bookDao.updateBook(book);
        }

        //清空购物车
        cart.clear();

        return orderId;
    }

    @Override
    public List<Order> myOrders(Integer userId) {
        return orderDao.queryMyOrders(userId);
    }

    @Override
    public OrderItem orderDetails(String orderId) {
        return orderItemDao.queryOrderDetailByOrderId(orderId);
    }

    @Override
    public List<Order> allOrders() {
        return orderDao.allOrders();
    }

    @Override
    public void sendOrder(String orderId) {
        orderDao.changOrderStatus(orderId);
    }

    @Override
    public void reveiveOrder(String orderId) {
        orderDao.changOrderStatus(orderId);
    }
}
