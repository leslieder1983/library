package com.manage.service.impl;

import com.manage.entity.Orders;
import com.manage.mapper.OrderMapper;
import com.manage.service.IOrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements IOrderService {

}
