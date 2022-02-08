package com.manage.service.impl;

import com.manage.entity.Blog;
import com.manage.mapper.BlogMapper;
import com.manage.service.IBlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

}
