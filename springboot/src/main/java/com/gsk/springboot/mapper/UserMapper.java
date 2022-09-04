package com.gsk.springboot.mapper;

import com.gsk.springboot.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper//实现将UserMapper的bean注入到springboot中
//mapper是数据库查询的接口，专门与数据库交互
public interface UserMapper {

    @Select("SELECT * from sys_user")
    List<User> findAll();

    @Insert("INSERT into sys_user(username,password,nickname,email,phone,address) " +
            "VALUES(#{username},#{password},#{nickname},#{email},#{phone},#{address})")
    int insert(User user);
}
