package com.gsk.springboot.mapper;

import com.gsk.springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gsk
 * @since 2022-09-07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /*List<User> selectAll();*/
}
