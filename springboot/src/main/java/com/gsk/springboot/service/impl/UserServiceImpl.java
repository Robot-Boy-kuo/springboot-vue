package com.gsk.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gsk.springboot.common.Constants;
import com.gsk.springboot.controller.dto.UserDTO;
import com.gsk.springboot.entity.User;
import com.gsk.springboot.exception.ServiceException;

import com.gsk.springboot.mapper.UserMapper;
import com.gsk.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gsk
 * @since 2022-09-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG =Log.get();


    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        //注意此处try-catch和throw的使用顺序
        User one;
        try {
            one = getOne(queryWrapper);//从数据库查询用户信息
        }catch (Exception e){
            //数据库中查到了不止一条数据
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }


    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one==null){
            one = new User();
            BeanUtils.copyProperties(userDTO,one);
            save(one);      //把one存到数据库
        }else{
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return one;


    }

    @Override
    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());

        //注意此处try-catch和throw的使用顺序
        User one;
        try {
            one = getOne(queryWrapper);//从数据库查询用户信息
        }catch (Exception e){
            //数据库中查到了不止一条数据
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if (one!=null && one.getPriority()==1){
            BeanUtils.copyProperties(one,userDTO);
            return userDTO;
        }else if(one!=null && one.getPriority()==0){
            throw new ServiceException(Constants.CODE_600,"权限不足！请联系管理员");
        }else{
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }

    }

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean deleteById(Integer id) {
        int affectedLines = userMapper.deleteById(id);
        return affectedLines != 0;
    }

    @Override
    public boolean deleteMulti(List<Integer> ids) {
        int affectedLines = userMapper.deleteBatchIds(ids);
        return affectedLines != 0;
    }

    @Override
    public boolean updateAndInsert(User user) {
        if (user.getId() == null){
            userMapper.insert(user);
        }else{
            userMapper.updateById(user);
        }
        return true;
    }


    @Override
    public User searchById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public Page<User> refresh(Integer pageNum, Integer pageSize, String username, String email, String address) {
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
        return page(new Page<>(pageNum, pageSize), queryWrapper);
    }
}
