package com.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

/**
 * @outhor Mr.JK
 * @create 2020-04-03  20:52
 */
public class JdbcUtils {

    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();

    static {

        try {
            Properties properties = new Properties();
            //读取jdbc.properties属性配置文件
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //从流中加载数据
            properties.load(inputStream);
            //创建 数据库连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

            //测试代码
            //System.out.println(dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }


    /**
     * 获取数据库连接池中的连接
     *
     * @return 返回null, 说明获取连接失败，有值就是获取连接成功
     */
    public static Connection getConnection() {
        Connection conn = conns.get();

        if (conn == null) {
            try {
                //从数据库连接池获取连接
                conn = dataSource.getConnection();
                conns.set(conn);//保存到ThreadLocal对象中，供后面jdbc使用

                conn.setAutoCommit(false);//设置为手动管理
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;


        //        Connection conn = null;

//        try {
//            conn = dataSource.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//        }
//        return conn;
    }

    /**
     * 提交事务，并关闭释放连接
     */
    public static void commitAndClose(){
        Connection conn = conns.get();
        if (conn != null){//如果不等于null，说明之前使用过数据库
            try {
                conn.commit();//提交事务

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();//关闭连接，释放资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        //一定要执行remove操作，否则就会出错，（因为Tomcat底层使用了线程池技术
        conns.remove();
    }

    /**
     * 回滚事务，并关闭释放连接
     */
    public static void rollbackAdnClose(){
        Connection conn = conns.get();
        if (conn != null){//如果不等于null，说明之前使用过数据库
            try {
                conn.rollback();//回滚事务

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();//关闭连接，释放资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        //一定要执行remove操作，否则就会出错，（因为Tomcat底层使用了线程池技术
        conns.remove();
    }



    /**
     * 关闭连接，放回数据库连接池

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
     */


}
