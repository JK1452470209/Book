package com.test;

import com.pojo.Cart;
import com.pojo.CartItem;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @outhor Mr.JK
 * @create 2020-04-08  20:30
 */
public class CartTest {
    private Cart cart = new Cart();

    @Test
    public void addItem() {
        cart.addItem(new CartItem(1,"java",1,new BigDecimal(100),new BigDecimal(100)));
        cart.addItem(new CartItem(1,"java",1,new BigDecimal(100),new BigDecimal(100)));
        cart.addItem(new CartItem(2,"数据结构",2,new BigDecimal(100),new BigDecimal(200)));
        System.out.println(cart);
    }

    @Test
    public void delete() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"java",1,new BigDecimal(100),new BigDecimal(100)));
        cart.addItem(new CartItem(1,"java",1,new BigDecimal(100),new BigDecimal(100)));
        cart.addItem(new CartItem(2,"数据结构",2,new BigDecimal(100),new BigDecimal(200)));
        cart.deleteItem(1);
        System.out.println(cart);
    }

    @Test
    public void clear() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"java",1,new BigDecimal(100),new BigDecimal(100)));
        cart.addItem(new CartItem(1,"java",1,new BigDecimal(100),new BigDecimal(100)));
        cart.addItem(new CartItem(2,"数据结构",2,new BigDecimal(100),new BigDecimal(200)));
        cart.clear();
        System.out.println(cart);
    }

    @Test
    public void updateCount() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"java",1,new BigDecimal(100),new BigDecimal(100)));
        cart.addItem(new CartItem(1,"java",1,new BigDecimal(100),new BigDecimal(100)));
        cart.clear();

        cart.addItem(new CartItem(2,"数据结构",2,new BigDecimal(100),new BigDecimal(200)));
        cart.updateCount(2,8);
        System.out.println(cart);
    }
}