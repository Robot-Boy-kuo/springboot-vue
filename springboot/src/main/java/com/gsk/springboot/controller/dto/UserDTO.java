package com.gsk.springboot.controller.dto;

import cn.hutool.core.annotation.Alias;
import lombok.Data;


/*
* 接收前端登录请求的参数
* */
@Data
public class UserDTO {
    @Alias("用户名")
    private String username;
    @Alias("密码")
    private String password;
    @Alias("昵称")
    private String nickname;
    @Alias("头像")
    private String avatarUrl;
}
