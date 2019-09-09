package com.zr.dao;

import com.zr.domain.Member;
import com.zr.domain.Orders;
import com.zr.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IOrdersDao {

    /**
     * 查询所有订单
     * @return
     * @throws Exception
     */
    @Select("select * from orders")
    @Results({
            @Result(id=true,column = "id",property = "id"),
            @Result(column = "orderNum",property = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "peopleCount",property = "peopleCount"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product", one = @One(select = "com.zr.dao.IProductDao.findById"))
    })
    List<Orders> findAll() throws Exception;

    /**
     * 多表操作，查询订单的详细信息
     * @param ordersId
     * @return
     * @throws Exception
     */
    @Select("select * from orders where id=#{ordersId}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderDesc", column = "orderDesc"),
            @Result(property = "product", column = "productId", javaType = Product.class, one = @One(select = "com.zr.dao.IProductDao.findById")),
            @Result(property = "member",column = "memberId",javaType = Member.class, one = @One(select = "com.zr.dao.IMemberDao.findById")),
            @Result(property = "travellers",column = "id",javaType =java.util.List.class, many = @Many(select = "com.zr.dao.ITravellerDao.findByOrdersId"))
    })
    Orders findById(String ordersId) throws Exception;

}
