package com.gsk.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gsk.springboot.common.Constants;
import com.gsk.springboot.common.Result;
import com.gsk.springboot.controller.dto.UserDTO;
import com.gsk.springboot.entity.User;
import com.gsk.springboot.service.IUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gsk
 * @since 2022-09-07
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){

        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        /*
         * 校验字符串
         * 1.非null
         * 2.长度不为0
         * */
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(userService.register(userDTO));

    }

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        /*
        * 校验字符串
        * 1.非null
        * 2.长度不为0
        * */
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO userDTO = userService.login(userDto);
        return Result.success(userDTO);
    }

    //新增或更新
    @PostMapping
    public Result save(@RequestBody User user){
        return Result.success(userService.saveOrUpdate(user));

    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(userService.deleteById(id));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        return Result.success(userService.deleteMulti(ids));
        }

    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }

    //查询所有
    @GetMapping("/")
    public Result findAll(){
        return Result.success(userService.list());
    }


    //根据id查询
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.searchById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String email,
                               @RequestParam(defaultValue = "") String address) {
        return Result.success(userService.refresh(pageNum,pageSize,username,email,address));
    }

    //实现导入和导出
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        //从数据库查询出所有数据
        List<User> list = userService.list();
        //通过工具类创建writer，写出到磁盘路径
        //在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义别名
        /*writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("phone","电话号码");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("createTime","创建时间");
        writer.addHeaderAlias("avatarUrl","头像");*/

        //在Entity中直接使用wutool的别名

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);

        /*设置浏览器写出响应的格式*/
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;character=utf-8");
        String fileName = URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();

    }

    @PostMapping("/import")
    public Result imp(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

        //在接收时需要指定类型进行接收
        List<User> list = reader.readAll(User.class);
        userService.saveBatch(list);
        System.out.println(list);
        return Result.success(true);
    }

}

