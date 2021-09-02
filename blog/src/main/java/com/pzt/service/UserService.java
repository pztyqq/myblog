package com.pzt.service;

import com.pzt.pojo.User;

public interface UserService {
    User checkUser(String username,String password);
}
