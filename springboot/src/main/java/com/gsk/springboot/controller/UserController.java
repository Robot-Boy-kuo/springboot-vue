package com.gsk.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import com.gsk.springboot.service.IUserService;
import com.gsk.springboot.entity.User;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gsk
 * @since 2022-09-07
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    //新增或更新
    @PostMapping
    public boolean save(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return userService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids){
        return userService.removeBatchByIds(ids);
        }

    //查询所有
    @GetMapping("/")
    public List<User> findAll(){
        return userService.list();
    }


    //根据id查询
    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id) {
        return userService.getById(id);
    }

    //分页查询
    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String email,
                               @RequestParam(defaultValue = "") String address) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //数据库中存在null值用字符串模糊匹配无法进行匹配，需要先进行判断
        if (!"".equals(username)){
            queryWrapper.like("username",username);//中间有AND
        }
        if (!"".equals(email)){
            queryWrapper.like("email", email);
        }
        if (!"".equals(address)){
            queryWrapper.like("address", address);
        }
        queryWrapper.orderByDesc("id");
        return userService.page(new Page<>(pageNum, pageSize), queryWrapper);

    }



}

