package com.bdy.seckill.service;


import com.bdy.seckill.dao.UserDao;
import com.bdy.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getDataById(Long id){

        return  userDao.getUserById(id);
    }
}
