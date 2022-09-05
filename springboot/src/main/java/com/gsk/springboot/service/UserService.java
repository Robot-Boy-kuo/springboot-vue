package com.gsk.springboot.service;

import com.gsk.springboot.entity.User;
import com.gsk.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public int save(User user){
        if (user.getId() == null){
            return userMapper.insert(user);//user没有id则表示是新增，否则为更新
        }else {
            return userMapper.update(user);
        }
    }
}
