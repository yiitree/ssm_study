package com.zr.service;

import com.zr.domain.Product;

import java.util.List;

public interface IProductService {

    /**
     * 查询所有产品
     * @return
     * @throws Exception
     */
    List<Product> findAll() throws Exception;

    /**
     * 保存产品信息
     * @param product
     * @throws Exception
     */
    void save(Product product) throws Exception;

}
