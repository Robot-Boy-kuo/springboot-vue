package com.gsk.springboot.service.impl;

import com.gsk.springboot.entity.User;
import com.gsk.springboot.mapper.UserMapper;
import com.gsk.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
