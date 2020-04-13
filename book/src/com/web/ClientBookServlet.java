package com.web;

import com.pojo.Book;
import com.pojo.Page;
import com.service.BookService;
import com.service.impl.BookServiceImpl;
import com.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @outhor Mr.JK
 * @create 2020-04-07  16:27
 */
public class ClientBookServlet extends BaseServlet{

    private BookService bookService = new BookServiceImpl();


    /**
     * 处理分页功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //System.out.println("经过前台ClientBookServlet");

        //1.获取请求的参数pageNo和pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        //2.调用BookServlet.page（pageNo,pageSize）:page对象
        Page<Book> page = bookService.page(pageNo,pageSize);

        page.setUrl("client/bookServlet?action=page");

        //3.保存Page对象到Request域中
        req.setAttribute("page",page);
        //4.请求转发到Pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }


    /**
     * 处理可选价格区间
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //System.out.println("经过前台ClientBookServlet");

        //1.获取请求的参数pageNo和pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.parseInt(req.getParameter("min"),0);
        int max = WebUtils.parseInt(req.getParameter("max"),Integer.MAX_VALUE);
        //2.调用BookServlet.page（pageNo,pageSize）:page对象
        Page<Book> page = bookService.pageByPrice(pageNo,pageSize,min,max);

        StringBuilder sb = new StringBuilder("client/bookServlet?action=pageByPrice");

        //如果有最小价格的参数，追加到分页条的地址参数中
        if (req.getParameter("min") != null){
            sb.append("&min=").append(req.getParameter("min"));
        }

        //如果有最大价格的参数，追加到分页条的地址参数中
        if (req.getParameter("max") != null){
            sb.append("&max=").append(req.getParameter("max"));
        }


        page.setUrl(sb.toString());



        //3.保存Page对象到Request域中
        req.setAttribute("page",page);
        //4.请求转发到Pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }


}
