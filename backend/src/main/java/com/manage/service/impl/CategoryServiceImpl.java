package com.manage.service.impl;

import com.manage.entity.Category;
import com.manage.mapper.CategoryMapper;
import com.manage.service.ICategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
