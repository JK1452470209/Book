package com.web;

import com.pojo.Book;
import com.pojo.Page;
import com.service.BookService;
import com.service.impl.BookServiceImpl;
import com.sun.xml.internal.bind.v2.model.core.ID;
import com.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-04-06  13:43
 */
public class BookServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();

    /**
     * 处理分页功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数pageNo和pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"),Page.PAGE_SIZE);

        //2.调用BookServlet.page（pageNo,pageSize）:page对象
        Page<Book> page = bookService.page(pageNo,pageSize);

        page.setUrl("manager/bookServlet?action=page");

        //3.保存Page对象到Request域中
        req.setAttribute("page",page);
        //4.请求转发到Pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);

    }


    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),0);
        pageNo+=1;

        //1.获取请求的参数==封装成为Book对象
        Book book = WebUtils.copyParmToBean(req.getParameterMap(), new Book());

        //2.调用BookService.addBook()保存图书
        bookService.addBook(book);

        //3.跳出图书列表页面
        //  /manager/bookServlet?action=list

        //这里跳转要用重定向，不能用请求转发，会有bug，F5刷新，会一直添加
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + pageNo);

    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数id，图书编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);


        //2.调用bookServlet.deleteBookById();删除图书
        bookService.deleteBookById(id);

        //3.重定向回图书列表管理页面
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));

    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数==封装称为Book对象
        Book book = WebUtils.copyParmToBean(req.getParameterMap(), new Book());

        //2.调用BookServlet.update
        bookService.updateBook(book);

        //3.重定向回图书列表管理页面
        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));

    }


    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数图书编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //2.调用bookServlet.queryBookId查询图书编号
        Book book = bookService.queryBookById(id);
        //3.保存图书到Request域中
        req.setAttribute("book",book);
        //4.请求转发到 pages/manager/book_edit.jsp页面
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);

    }

    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.通过BookServlet查询全部图书
        List<Book> books = bookService.queryBooks();
        //2.把全部图书保存到Request域中
        req.setAttribute("books",books);
        //3.请求转发到/pages/manager/book_manager.jsp
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);

    }

}
