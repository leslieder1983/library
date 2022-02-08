package com.manage.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.manage.common.lang.Result;
import com.manage.entity.Blog;
import com.manage.service.IBlogService;
import com.manage.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wu
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    IBlogService blogService;

    @GetMapping("/list")
    public Result list(@PathParam("current") Integer current, @PathParam("size") Integer size) {
        current=current==null?1:current;
        size=size==null?5:size;
        Page page = new Page(current,size);
        Page pageData = blogService.page(page,new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.success(pageData);
    }


    @GetMapping("/detail")
    public Result detail(@PathParam("id") Integer id ) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该公告已删除");
        return Result.success(blog);
    }
    //删除单个，参数id
    @GetMapping("/delete")
    public Result delete(@PathParam("id") Integer id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该公告已删除");
        return Result.success(blogService.removeById(id));
    }
    @RequiresAuthentication
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog tmp = null;
        if (blog.getId() != null) {
            tmp = blogService.getById(blog.getId());
            //只能编辑自己的文章
            Assert.isTrue(tmp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");
        } else {
            tmp = new Blog();
            tmp.setUserId(ShiroUtil.getProfile().getId());
            tmp.setCreated(LocalDateTime.now());
            tmp.setStatus(0);
        }
        BeanUtil.copyProperties(blog, tmp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(tmp);
        return Result.success(null);
    }
}
