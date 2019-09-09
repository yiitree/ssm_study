package com.zr.dao;

import com.zr.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IProductDao {

    /**
     * 查询所有商品
     * @return
     * @throws Exception
     */
    @Select("select * from product")
    List<Product> findAll() throws Exception;

    /**
     * 保存商品
     * @param product
     * @throws Exception
     */
    @Insert(" insert into product (productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values (#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    void save(Product product) throws Exception;

    /**
     * 根据id获取产品信息
     * @param id
     * @return
     * @throws Exception
     */
    @Select("select * from product where id=#{id}")
    Product findById(String id) throws Exception;


}
