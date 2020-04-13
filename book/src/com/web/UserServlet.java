package com.web;

import com.google.gson.Gson;
import com.pojo.User;
import com.service.UserService;
import com.service.impl.UserServiceImpl;
import com.utils.WebUtils;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @outhor Mr.JK
 * @create 2020-04-05  16:33
 */
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    /**
     * 用ajax验证用户是否存在，并将数据传输到客户端
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数username
        String username = req.getParameter("username");
        //调用userService.existUsername
        boolean existUsername = userService.existUsername(username);
        //把返回的结果封装成为map对象
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("existUsername",existUsername);

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        resp.getWriter().write(json);
    }


        /**
         * 处理登录的功能
         * @param req
         * @param resp
         * @throws ServletException
         * @throws IOException
         */
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取连接
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //调用userService.login登录处理业务
        User loginUser = userService.login(new User(null, username, password, null));

        if (loginUser == null){
            //调回登录页面
            //把错误信息，和回显的表单项信息，保存到Request域中
            req.setAttribute("msg","用户名或密码错误！");
            req.setAttribute("username",username);
            //System.out.println("登录失败");
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        }else {
            //登录成功页面
            //System.out.println("登录成功");

            //将登陆成功的信息保存报Session域
            req.getSession().setAttribute("user",loginUser);

            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }
    }

    /**
     * 处理注册的功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取Session中的验证码
        String token = (String)req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //删除Session中的验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);


        //1.获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");


        User user = WebUtils.copyParmToBean(req.getParameterMap(), new User());

        //2.检查 验证码是否正确 ==== 用谷歌验证码
        if (token != null && token.equalsIgnoreCase(code)){

            //3.检查 用户名是否可用
            if (userService.existUsername(username)){
                //不可用
                System.out.println("用户名[" + username + "]已存在！");
                //把回显信息，保存到Request域中
                req.setAttribute("msg","用户名已存在!");
                req.setAttribute("username",username);
                req.setAttribute("email",email);

                //跳回注册页面
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);

            }else{
                //可用，调用Service保存到数据库
                userService.registUser(new User(null,username,password,email));
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,resp);

            }

        }else {
            //把回显信息，保存到Request域中
            req.setAttribute("msg","验证码错误!");
            req.setAttribute("username",username);
            req.setAttribute("email",email);

            System.out.println("验证码[" + code +"]错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
        }
    }



    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.消耗Session中用户登录的信息（或销毁Session
        req.getSession().invalidate();
        //2.重定向到首页（或登录页面
        resp.sendRedirect(req.getContextPath());
    }


}
