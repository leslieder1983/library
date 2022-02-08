package com.manage.service.impl;

import com.manage.entity.Book;
import com.manage.mapper.BookMapper;
import com.manage.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wu
 * @since 2022-01-29
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

}
