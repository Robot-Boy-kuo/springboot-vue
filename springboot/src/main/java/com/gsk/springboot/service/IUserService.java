package com.gsk.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gsk.springboot.controller.dto.UserDto;
import com.gsk.springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gsk
 * @since 2022-09-07
 */
public interface IUserService extends IService<User> {
    public boolean deleteById(Integer id);

    public boolean deleteMulti(List<Integer> ids);

    public boolean updateAndInsert(User user);

    public User searchById(Integer id);

    public Page<User> refresh(Integer pageNum,
                              Integer pageSize,
                              String username,
                              String email,
                              String address);

    boolean login(UserDto user);
}
