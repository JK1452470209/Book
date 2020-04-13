package com.test;

import com.dao.OrderItemDao;
import com.dao.impl.OrderItemDaoImpl;
import com.pojo.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @outhor Mr.JK
 * @create 2020-04-09  15:22
 */
public class OrderItemDaoImplTest {
    OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Test
    public void saveOrderItem() {

        orderItemDao.saveOrderItem(new OrderItem(null,"java从入门到精通",1,new BigDecimal(100),new BigDecimal(100),"123456789"));
        orderItemDao.saveOrderItem(new OrderItem(null,"c从入门到精通",2,new BigDecimal(100),new BigDecimal(200),"123456789"));
        orderItemDao.saveOrderItem(new OrderItem(null,"c++从入门到精通",1,new BigDecimal(100),new BigDecimal(100),"123456789"));
    }
    @Test
    public void queryOrderDetailByOrderId() {
        OrderItem orderItem = orderItemDao.queryOrderDetailByOrderId("15864221915271");
        System.out.println(orderItem);

    }



}