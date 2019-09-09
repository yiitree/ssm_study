package com.zr.domain;

import com.zr.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 产品列表
 */
public class Product {

    private String id;              // 主键
    private String productNum;      // 编号唯一
    private String productName;     // 产品名称
    private String cityName;        // 出发城市
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") // 客户端传入字符串格式，进行转换
    private Date departureTime;     // 出发时间
    private String departureTimeStr;
    private Double productPrice;    // 产品价格
    private String productDesc;     // 产品描述
    private Integer productStatus;  // 状态 0关闭 1开启
    private String productStatusStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureTimeStr() {   //出发时间
        if(departureTime != null){
            departureTimeStr = DateUtils.date2String(departureTime,"yyyy-MM-dd HH:mm:ss");
        }
        return departureTimeStr;
    }

    public void setDepartureTimeStr(String departureTimeStr) {
        this.departureTimeStr = departureTimeStr;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductStatusStr() {   // 产品描述
        if(productStatus!=null){
            // 状态 0 关闭 1 开启
            if(productStatus == 0){
                productStatusStr="关闭";
            }
            if (productStatus == 1){
                productStatusStr="打开";
            }
        }
        return productStatusStr;
    }

    public void setProductStatusStr(String productStatusStr) {
        this.productStatusStr = productStatusStr;
    }
}
