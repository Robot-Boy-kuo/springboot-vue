package com.gsk.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsk.springboot.entity.User;
import com.gsk.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    public boolean saveUser(User user) {
        /*if (user.getId() == null){
            return save(user);
        }else{
            return updateById(user);
        }*/
        return saveOrUpdate(user);
        /*boolean save = save(user);*///mybatis-plus提供的方法，表示插入数据
        //return false;
    }
/*    @Autowired
    private UserMapper userMapper;*/

/*    public int save(User user){
        if (user.getId() == null){
            return userMapper.insert(user);//user没有id则表示是新增，否则为更新
        }else {
            return userMapper.update(user);
        }
    }*/
}
