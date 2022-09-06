package com.gsk.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//lombok 设置默认的setter和getter，简化代码
@NoArgsConstructor//lombok 创建无参构造方法
@AllArgsConstructor//lombok 创建有参构造方法
@TableName(value = "sys_user")
public class User {
    @TableId//指定主键
    private Integer id;
    private String username;
    //不展示密码，忽略某个实体不展示给前端
    @JsonIgnore
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;

}
