package com.web;

import com.google.gson.Gson;
import com.pojo.Book;
import com.pojo.Cart;
import com.pojo.CartItem;
import com.service.BookService;
import com.service.impl.BookServiceImpl;
import com.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UnknownFormatConversionException;

/**
 * @outhor Mr.JK
 * @create 2020-04-08  20:49
 */
public class CartServlet extends BaseServlet{

    private BookService bookService = new BookServiceImpl();

    /**
     * 修改商品数量
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        int count = WebUtils.parseInt(req.getParameter("count"),0);
        //获取Cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if (cart != null){
            cart.updateCount(id,count);
            //重定向回购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }



        /**
         * 清空购物车
         * @param req
         * @param resp
         * @throws ServletException
         * @throws IOException
         */
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null){
            //清空购物车
            cart.clear();
            //重定向回购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));
        }

    }



        /**
         * 删除商品项
         * @param req
         * @param resp
         * @throws ServletException
         * @throws IOException
         */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取商品编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //获取购物车对象
        Cart cart = (Cart)req.getSession().getAttribute("cart");

        if (cart != null){
            //删除了购物车商品项
            cart.deleteItem(id);
            //重定向回购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));
        }



    }

        /**
         * 加入购物车
         * @param req
         * @param resp
         * @throws ServletException
         * @throws IOException
         */
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        System.out.println("加入购物车");
//        System.out.println("商品编号：" + req.getParameter("id"));

        //获取请求的参数 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //调用bookServlet.queryBookById(id):Book得到图书的信息
        Book book = bookService.queryBookById(id);
        //把图书信息，转换称为CartItem商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        //调用Cart.addItem(CartItem);添加商品下个
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);

        System.out.println(cart);
        System.out.println("请求头Referer的值：" + req.getHeader("Referer"));

        //最后一个添加的商品的名称
        req.getSession().setAttribute("lastName",cartItem.getName());

        //重定向回商品列表页面
        resp.sendRedirect(req.getHeader("Referer"));


    }

    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        System.out.println("加入购物车");
//        System.out.println("商品编号：" + req.getParameter("id"));

        //获取请求的参数 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //调用bookServlet.queryBookById(id):Book得到图书的信息
        Book book = bookService.queryBookById(id);
        //把图书信息，转换称为CartItem商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        //调用Cart.addItem(CartItem);添加商品下个
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);

        System.out.println(cart);
        System.out.println("请求头Referer的值：" + req.getHeader("Referer"));

        //最后一个添加的商品的名称
        req.getSession().setAttribute("lastName",cartItem.getName());

       Map<String,Object> resultMap = new HashMap<>();

       resultMap.put("totalCount",cart.getTotalCount());
       resultMap.put("lastName",cartItem.getName());

        Gson gson = new Gson();
        String resultMapJsonString = gson.toJson(resultMap);
        resp.getWriter().write(resultMapJsonString);


    }
}
