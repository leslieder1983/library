package com.manage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.manage.common.lang.Result;
import com.manage.entity.Category;
import com.manage.service.ICategoryService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Arrays;

/**
 * <p>
 *  教材分类
 * </p>
 *
 * @author wu
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;
    @GetMapping("/list")
    public Result list(@PathParam("current") Integer current,@PathParam("size") Integer size){
        current=current==null?1:current;
        size=size==null?5:size;
        Page page = new Page(current,size);
        Page page1 = categoryService.page(page,new QueryWrapper<Category>().orderByDesc("id"));
        return Result.success(page1);
    }
    @GetMapping("/all")
    public Result list(){
        return Result.success(categoryService.list());
    }
    //添加数据
    @PostMapping("add")
    public Result save(@Validated @RequestBody Category category){
        Category category1 = categoryService.getOne(new QueryWrapper<Category>().eq("classes", category.getClasses()),false);
        //防止名重复
        if(category1!=null) {
            return Result.fail("该分类已存在，请更换类别名！");
        }
        categoryService.save(category);
        return Result.success("添加成功");
    }
    //更新单条数据,实体传入id,不能更改创建时间
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Category category){
        UpdateWrapper<Category> bookUpdateWrapper = new UpdateWrapper<Category>();
        bookUpdateWrapper.eq("id",category.getId());
        categoryService.update(category,bookUpdateWrapper);
        return Result.success("更新成功") ;
    }

    //删除单个，参数id
    @RequiresAuthentication
    @GetMapping("/delete")
    public Result delete(@PathParam("id") Integer id){
        Category category = categoryService.getById(id);
        Assert.notNull(category, "该教材已删除");
        return Result.success(categoryService.removeById(id));
    }

    @RequiresAuthentication
    //删除所有，参数：字符串数组
    @PostMapping("/deleteAll")
    public Result deleteAll(@RequestBody String[] idList ){
        return Result.success( categoryService.removeByIds(Arrays.asList(idList))) ;
    }
}
