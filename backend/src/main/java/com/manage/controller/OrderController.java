package com.manage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.manage.common.lang.Result;
import com.manage.entity.Orders;
import com.manage.service.IOrderService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
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
 *  订单
 * </p>
 *
 * @author wu
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IOrderService orderService;
    @GetMapping("/list")
    public Result list(@PathParam("current") Integer current, @PathParam("size") Integer size){
        current=current==null?1:current;
        size=size==null?5:size;
        Page page = new Page(current,size);
        Page page1 = orderService.page(page,new QueryWrapper<Orders>().orderByDesc("create_time"));
        return Result.success(page1);
    }
    //添加数据
    @PostMapping("add")
    public Result save(@Validated @RequestBody Orders order){
        LocalDateTime date=LocalDateTime.now();
        order.setCreateTime(date);
        orderService.save(order);
        return Result.success("添加成功");
    }
    //更新单条数据,实体传入id,不能更改创建时间
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Orders order){
        UpdateWrapper<Orders> orderUpdateWrapper = new UpdateWrapper<Orders>();
        orderUpdateWrapper.eq("id",order.getId());
        orderService.update(order,orderUpdateWrapper);
        return Result.success("更新成功") ;
    }

    //删除单个，参数id
    @RequiresAuthentication
    @GetMapping("/delete")
    public Result delete(@PathParam("id") Integer id){
        Orders order = orderService.getById(id);
        Assert.notNull(order, "该教材已删除");
        return Result.success(orderService.removeById(id));
    }

    @RequiresAuthentication
    //删除所有，参数：字符串数组
    @PostMapping("/deleteAll")
    public Result deleteAll(@RequestBody String[] idList ){
        return Result.success( orderService.removeByIds(Arrays.asList(idList))) ;
    }
    //条件查询
    @PostMapping("/query")
    public Result query(@RequestBody Orders order ){
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<Orders>().like("bookname", order.getBookname());
        Map<String,Object> params=new HashMap<>();
        params.put("bookname",order.getBookname());
        params.put("agent",order.getAgent());
        //如果为空则忽略
        queryWrapper.allEq(params, false);
        List<Orders> orderList = orderService.list(queryWrapper);
        return Result.success( orderList) ;
    }
}
