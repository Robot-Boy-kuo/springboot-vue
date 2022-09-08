package com.gsk.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 接口统一返回包装类
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {



    //判断请求成功还是失败
    private String code;
    //失败原因
    private String message;
    //后台携带的数据
    private Object data;

    public static Result success(){
        return new Result(Constants.CODE_200,"",null);
    }

    public static Result success(Object data){
        return new Result(Constants.CODE_200,"",data);
    }

    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }

    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }


}
