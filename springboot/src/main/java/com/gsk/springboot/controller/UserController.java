package com.gsk.springboot.controller;

import com.gsk.springboot.entity.User;
import com.gsk.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//controller层，与浏览器进行信息交互
@RestController
@RequestMapping("/user")//统一给接口加前缀，必须和下面的拼接才是真实的url
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping//向数据库插入或修改数据、删除
    /*
    * @RequestBody：在前台传入json数据的时候，可以把json数据映射成User对象
    * user对象是从接口传进来的
    * */
    public Integer save(@RequestBody User user){
        return userMapper.insert(user);
    }


    @GetMapping("/")
    public List<User> index(){
        return userMapper.findAll();
    }
}
