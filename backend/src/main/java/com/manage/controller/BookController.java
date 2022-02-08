package com.manage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.manage.common.lang.Result;
import com.manage.entity.Book;
import com.manage.service.IBookService;
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
 *  教材管理
 * </p>
 *
 * @author wu
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    IBookService bookService;


    @GetMapping("/list")
    public Result list(@PathParam("current") Integer current,@PathParam("size") Integer size){
        current=current==null?1:current;
        size=size==null?5:size;
        Page page = new Page(current,size);
        Page page1 = bookService.page(page,new QueryWrapper<Book>().orderByDesc("create_time"));
        return Result.success(page1);
    }
    @GetMapping("/all")
    public Result all(){
        return Result.success(bookService.list());
    }
    //根据id获取当前图书详细数据
    @GetMapping("/detail")
    public Result detail(@PathParam("id") Integer id){
        Book book = bookService.getById(id);
        Assert.notNull(book,"该图书不存在");
        return Result.success(book);
    }
    //添加数据
    @PostMapping("/add")
    public Result save(@Validated @RequestBody Book book){
        Book book1 = bookService.getOne(new QueryWrapper<Book>().eq("book_name", book.getBookName()),false);
        //防止书名重复
        if(book1!=null) {
            return Result.fail("该教材已存在，请更换教材名！");
        }
        LocalDateTime date=LocalDateTime.now();
        book.setCreateTime(date);
        bookService.save(book);
        return Result.success("添加成功");
    }
    //更新单条数据,实体传入id,不能更改创建时间
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Book book){
        UpdateWrapper<Book> bookUpdateWrapper = new UpdateWrapper<Book>();
        bookUpdateWrapper.eq("id",book.getId());
        bookService.update(book,bookUpdateWrapper);
        return Result.success("更新成功") ;
    }

    //删除单个，参数id
    @RequiresAuthentication
    @GetMapping("/delete")
    public Result delete(@PathParam("id") Integer id){
        Book book = bookService.getById(id);
        Assert.notNull(book, "该教材已删除");
        return Result.success(bookService.removeById(id));
    }

    @RequiresAuthentication
    //删除所有，参数：字符串数组
    @PostMapping("/deleteAll")
    public Result deleteAll(@RequestBody String[] idList ){
        return Result.success( bookService.removeByIds(Arrays.asList(idList))) ;
    }
    //条件查询
    @PostMapping("/query")
    public Result query(@RequestBody Book book ){
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>().like("book_name", book.getBookName());
        Map<String,Object> params=new HashMap<>();
        params.put("book_name",book.getBookName());
        params.put("isbn",book.getIsbn());
        //如果为空则忽略
        queryWrapper.allEq(params, false);
        List<Book> books = bookService.list(queryWrapper);
        return Result.success( books) ;
    }
}
