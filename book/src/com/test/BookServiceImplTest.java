package com.test;

import com.pojo.Book;
import com.pojo.Page;
import com.service.BookService;
import com.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @outhor Mr.JK
 * @create 2020-04-06  13:37
 */
public class BookServiceImplTest {
    private BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
        bookService.addBook(new Book(null, "帅哥", "坤哥", new BigDecimal(999), 1, 1, null));

    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(27);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(21, "帅哥秘籍", "坤哥", new BigDecimal(999), 1, 1, null));

    }

    @Test
    public void queryBookById() {
        System.out.println(bookService.queryBookById(27));

    }

    @Test
    public void queryBooks() {
        for (Book querybook : bookService.queryBooks()) {
            System.out.println(querybook);
        }
    }

    @Test
    public void page(){
        System.out.println(bookService.page(1,4));
    }

    @Test
    public void pageByPrice() {
        System.out.println(bookService.pageByPrice(1,Page.PAGE_SIZE,10,50));
    }
}