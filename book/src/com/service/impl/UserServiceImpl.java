package com.service.impl;

import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.pojo.User;
import com.service.UserService;

/**
 * @outhor Mr.JK
 * @create 2020-04-04  10:11
 */
public class UserServiceImpl implements UserService {

    private UserDao userdao = new UserDaoImpl();

    @Override
    public void registUser(User user) {
            userdao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userdao.queryUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existUsername(String username) {

        if (userdao.queryUserByUsername(username) == null){
            //等于null，说明没查到，表示可用
            return false;
        }
        return true;
    }

}
