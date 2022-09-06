package com.gsk.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    /*@Autowired
    private UserMapper userMapper;*/

    @Autowired
    private UserService userService;//使用UserService对数据库进行保存，更新和新增

    //新增和修改
    @PostMapping//向数据库插入或修改数据、删除
    /*
    * @RequestBody：在前台传入json数据的时候，可以把json数据映射成User对象
    * user对象是从接口传进来的
    * */
    public boolean save(@RequestBody User user){
        return userService.saveUser(user);
    }

    //查询
    @GetMapping("/")
    public List<User> findAll(){
        return userService.list();
    }
    /*@GetMapping("/")
    public String index(){
        return "ok";
    }*/

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return userService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return userService.removeBatchByIds(ids);
    }

    //分页查询接口
    /*
    * @RequestParam 接受 ？ pageNum = 1 & pageSize = 10
    * limit 第一个参数：(pageNum - 1) * pageSize
    * limit 第二个参数：PageSize
    * */
    /*@GetMapping("/page")       //接口路径/user/page
    public Map<String, Object> findpage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String username){
        pageNum = (pageNum - 1) * pageSize;
        List<User> data =  userMapper.selectPage(pageNum,pageSize,username);
        //查询总条数
        Integer total = userMapper.selectTotal(username);
        Map<String, Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total",total);
        return res;
    }*/


    //分页查询：mybatis-plus
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address){
        //构建page参数
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

        //数据库中存在null值用字符串模糊匹配无法进行匹配，需要先进行判断
        if (!"".equals(username)){
            userQueryWrapper.like("username",username);//中间有AND
        }
        if (!"".equals(email)){
            userQueryWrapper.like("email", email);
        }
        if (!"".equals(address)){
            userQueryWrapper.like("address", address);
        }

        //查询条件以or拼接
        /*userQueryWrapper.or().like("username",username);*/
        return userService.page(page, userQueryWrapper);
    }



}
