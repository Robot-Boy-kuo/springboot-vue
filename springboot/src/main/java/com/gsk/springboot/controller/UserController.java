package com.gsk.springboot.controller;

import com.gsk.springboot.entity.User;
import com.gsk.springboot.mapper.UserMapper;
import com.gsk.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//controller层，与浏览器进行信息交互
@RestController
@RequestMapping("/user")//统一给接口加前缀，必须和下面的拼接才是真实的url
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;//使用UserService对数据库进行保存，更新和新增

    //新增和修改
    @PostMapping//向数据库插入或修改数据、删除
    /*
    * @RequestBody：在前台传入json数据的时候，可以把json数据映射成User对象
    * user对象是从接口传进来的
    * */
    public Integer save(@RequestBody User user){
        return userService.save(user);
    }


    //查询
    @GetMapping("/")
    public List<User> findAll(){
        return userMapper.findAll();
    }
    /*@GetMapping("/")
    public String index(){
        return "ok";
    }*/

    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id){
        return userMapper.deleteById(id);
    }

    //分页查询接口
    /*
    * @RequestParam 接受 ？ pageNum = 1 & pageSize = 10
    * limit 第一个参数：(pageNum - 1) * pageSize
    * limit 第二个参数：PageSize
    * */
    @GetMapping("/page")       //接口路径/user/page
    public Map<String, Object> findpage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        pageNum = (pageNum - 1) * pageSize;
        List<User> data =  userMapper.selectPage(pageNum,pageSize);
        //查询总条数
        Integer total = userMapper.selectTotal();
        Map<String, Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;

    }
}
