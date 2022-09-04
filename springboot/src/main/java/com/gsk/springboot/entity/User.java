package com.gsk.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//lombok 设置默认的setter和getter，简化代码
@NoArgsConstructor//lombok 创建无参构造方法
@AllArgsConstructor//lombok 创建有参构造方法
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;

}
