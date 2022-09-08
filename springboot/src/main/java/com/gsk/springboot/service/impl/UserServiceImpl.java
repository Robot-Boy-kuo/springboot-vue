package com.gsk.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gsk.springboot.entity.User;
import com.gsk.springboot.mapper.UserMapper;
import com.gsk.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
