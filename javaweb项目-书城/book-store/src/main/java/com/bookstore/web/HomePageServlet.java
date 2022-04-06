package com.bookstore.web;

import com.bookstore.beans.Book;
import com.bookstore.beans.Page;
import com.bookstore.service.BookService;
import com.bookstore.service.impl.BookServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 首页模块的servlet
 */
public class HomePageServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();
    private Logger logger = Logger.getLogger(HomePageServlet.class);

    /**
     * 首页的分页功能
     */
    public void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解析请求
        int pageNo = req.getParameter("pageNo") == null ? 1 :
                Integer.parseInt(req.getParameter("pageNo"));
        int pageSize = req.getParameter("pageSize") == null ? Page.PAGE_SIZE :
                Integer.parseInt(req.getParameter("pageSize"));

        // 调用service层的page方法，获取Page对象
        Page<Book> page = bookService.page(pageNo, pageSize);
        page.setUriPart("/book-store/client/book?action=page");
        // 保存page对象到request域中
        req.setAttribute("page", page);

        // 不让浏览器缓存
        resp.setDateHeader("Expires", -1); // for IE
        resp.setHeader("Cache-Control", "no-cache"); // for 火狐 或 其他。
        resp.setHeader("Pragma", "no-cache"); // for 火狐 或 其他。
        // 请求转发到bookStore/pages/manager/book_manager.jsp
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req, resp);
    }

    // 首页，在价格区间的基础上进行分页
    public void pageByPrice(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取前端页面传回的最大价格和最小价格以及页码和每页数据条数
        double minPrice = Double.parseDouble(req.getParameter("min"));
        double maxPrice = Double.parseDouble(req.getParameter("max"));
        int pageNo = req.getParameter("pageNo") == null ? 1 :
                Integer.parseInt(req.getParameter("pageNo"));
        int pageSize = req.getParameter("pageSize") == null ? Page.PAGE_SIZE :
                Integer.parseInt(req.getParameter("pageSize"));

        // 根据最大价格和最小价格来获取页面数据，并且设置页面对应的URI
        Page<Book> page = bookService.pageByPrice(minPrice, maxPrice, pageNo, pageSize);
        page.setUriPart("/book-store/client/book?action=pageByPrice&min=" + minPrice + "&max=" + maxPrice);


        // 把请求转发到首页
        req.setAttribute("page", page);
        req.setAttribute("minPrice", minPrice);
        req.setAttribute("maxPrice", maxPrice);
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req, resp);
    }


}
