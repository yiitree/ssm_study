package com.zr.service;

import com.zr.domain.Orders;

import java.util.List;

public interface IOrdersService {

    /**
     * 查询所有订单
     * @return
     * @throws Exception
     */
    List<Orders> findAll(int page, int size) throws Exception;

    Orders findById(String ordersId) throws Exception;
}
