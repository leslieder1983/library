package com.manage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.manage.common.dto.PsdDto;
import com.manage.common.dto.UserDto;
import com.manage.common.lang.Result;
import com.manage.entity.User;
import com.manage.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wu
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;
    @GetMapping("/detail")
    public Result detail(@PathParam("id") Integer id){
        return Result.success(userService.getById(id));
    }

    @GetMapping("/list")
    public Result list(@PathParam("current") Integer current,@PathParam("size") Integer size){
        current=current==null?1:current;
        size=size==null?5:size;
        Page page = new Page(current,size);
        Page page1 = userService.page(page,new QueryWrapper<User>().orderByDesc("created"));
        return Result.success(page1);
    }
    //添加数据
    @PostMapping("add")
    public Result save(@Validated @RequestBody User user){
        User user1 = userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()),false);
        //防止用户名重复
        if(user1!=null) {
            return Result.fail("用户名已存在，请更换用户名！");
        }
        //md5加密
        String md5password=   DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5password);
        LocalDateTime date=LocalDateTime.now();
        user.setCreated(date);
        userService.save(user);
        return Result.success("添加成功");
    }
    //更新单条数据,实体传入id,不能更改创建时间与密码
    @PostMapping("/update")
    public Result update(@Validated @RequestBody UserDto user){
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<User>();
        userUpdateWrapper.eq("id",user.getId());
        User user1=new User();
        BeanUtils.copyProperties(user,user1);
        userService.update(user1,userUpdateWrapper);
        return Result.success("更新成功") ;
    }
    //更新单条数据的密码
    @PostMapping("/password")
    public Result updatePassword(@RequestBody PsdDto psdDto){
        //原密码不正确，直接返回
        String pass=  DigestUtils.md5DigestAsHex(psdDto.getOldPassword().getBytes());
        if(!userService.getById(psdDto.getId()).getPassword().equals(pass)){
            return  Result.fail("原密码不正确，请重试");
        }
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        String md5password=   DigestUtils.md5DigestAsHex(psdDto.getNewPassword().getBytes());
        userUpdateWrapper.eq("id",psdDto.getId()).set("password",md5password);
        userService.update(userUpdateWrapper);
        return Result.success("更新成功") ;
    }
    //删除单个，参数id
    @RequiresAuthentication
    @GetMapping("/delete")
    public Result delete(@PathParam("id") Integer id){
        User user = userService.getById(id);
        Assert.notNull(user, "该用户已删除");
        return Result.success(userService.removeById(id));
    }

    @RequiresAuthentication
    //删除所有，参数：字符串数组
    @PostMapping("/deleteAll")
    public Result deleteAll(@RequestBody String[] idList ){
        return Result.success( userService.removeByIds(Arrays.asList(idList))) ;
    }
    //条件查询
    @PostMapping("/query")
    public Result query(@RequestBody User user ){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().like("username", user.getUsername());
        Map<String,Object> params=new HashMap<>();
        params.put("username",user.getUsername());
        params.put("phone",user.getPhone());
        //如果为空则忽略
        queryWrapper.allEq(params, false);
        List<User> users = userService.list(queryWrapper);
        return Result.success( users) ;
    }
}
