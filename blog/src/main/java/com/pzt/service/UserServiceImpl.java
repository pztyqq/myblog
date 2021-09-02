package com.pzt.service;

import com.pzt.dao.UserRepository;
import com.pzt.pojo.User;
import com.pzt.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User
 *
 * @author 蒲正庭 on 2021/7/30
 */
@Service
public class UserServiceImpl implements UserService {

    //注入
    @Autowired
    private UserRepository userRepository;

    //检查用户名和密码
    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
