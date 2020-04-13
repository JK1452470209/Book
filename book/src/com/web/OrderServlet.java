package com.web;

import com.pojo.Cart;
import com.pojo.Order;
import com.pojo.OrderItem;
import com.pojo.User;
import com.service.OrderService;
import com.service.impl.OrderServiceImpl;
import com.utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-04-09  15:47
 */
public class OrderServlet extends BaseServlet {
    
    private OrderService orderService = new OrderServiceImpl();

    protected void sendOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取订单号
        String orderId = req.getParameter("orderId");
        //更改状态 0 - 1
        orderService.sendOrder(orderId);

        //返回原来的页面
        //重定向回商品列表页面
        resp.sendRedirect(req.getHeader("Referer"));

    }

    protected void receiveOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取订单号
        String orderId = req.getParameter("orderId");
        //更改状态 1 - 2
        orderService.reveiveOrder(orderId);

        //重定向回商品列表页面
        resp.sendRedirect(req.getHeader("Referer"));
        //req.getRequestDispatcher("pages/order/order.jsp").forward(req,resp);

    }



    protected void allOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = orderService.allOrders();
        req.getSession().setAttribute("orders",orders);

        //转发
        req.getRequestDispatcher("pages/manager/order_manager.jsp").forward(req,resp);
    }



    protected void orderDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        //根据订单号查询详细信息
        OrderItem orderItem = orderService.orderDetails(orderId);
        //将查询到的详细信息存到域中
        req.getSession().setAttribute("orderItem",orderItem);

        //转发
        req.getRequestDispatcher("pages/order/detail_order.jsp").forward(req,resp);
    }



        /**
         * 查看我的订单
         * @param req
         * @param resp
         * @throws ServletException
         * @throws IOException
         */
    protected void myOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        //根据用户id查看订单
        List<Order> orders = orderService.myOrders(Integer.parseInt(id));

        //保存到request域
        req.getSession().setAttribute("orders",orders);

        //请求转发到页面
        req.getRequestDispatcher("pages/order/order.jsp").forward(req,resp);

    }

    /**
     * 生产订单
     * @param req
     * @param resp
     */
    protected void createorder(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        //先获取Cart购物车对象
        Cart cart = (Cart)req.getSession().getAttribute("cart");
        //获取用户信息，获取UserId
        User loginuser = (User) req.getSession().getAttribute("user");

        if (loginuser == null){
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            return;
        }

        Integer userId = loginuser.getId();
        //调用orderService.createOrder(Cart,Userid);,生产订单
        String orderId = orderService.createOrder(cart, userId);



        //req.setAttribute("orderId",orderId);
        //请求转发
        //req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req,resp);

        req.getSession().setAttribute("orderId",orderId);

        resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
    }

}
