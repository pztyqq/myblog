package com.pzt.dao;

import com.pzt.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

//继承JpaRepository，继承里面的增删改查
public interface UserRepository extends JpaRepository<User,Long> {
    //遵循命名规则
    User findByUsernameAndPassword(String username,String password);
}
