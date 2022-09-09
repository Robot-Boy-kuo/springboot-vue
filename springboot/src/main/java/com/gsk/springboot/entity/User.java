package com.gsk.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 
 * </p>
 *
 * @author gsk
 * @since 2022-09-07
 */
@Getter
@Setter
  @TableName("sys_user")
@ApiModel(value = "User对象", description = "")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @Alias("用户名")
      @ApiModelProperty("用户名")
      private String username;

      @Alias("密码")
      @ApiModelProperty("密码")
      //@JsonIgnore
      private String password;

      @JsonIgnore
      public String getPassword() {
        return password;
      }

      @JsonProperty
      public void setPassword(String password) {
        this.password = password;
      }

  @Alias("昵称")
      @ApiModelProperty("昵称")
      private String nickname;

      @Alias("邮箱")
      @ApiModelProperty("邮箱")
      private String email;

      @Alias("电话号码")
      @ApiModelProperty("电话号码")
      private String phone;

      @Alias("地址")
      @ApiModelProperty("地址")
      private String address;

      @Alias("创建时间")
      @ApiModelProperty("创建时间")
      private LocalDateTime createTime;

      @Alias("头像")
      @ApiModelProperty("头像")
      private String avatarUrl;

      private int priority;

}
