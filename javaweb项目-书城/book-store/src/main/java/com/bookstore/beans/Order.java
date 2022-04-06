package com.bookstore.beans;

import java.io.Serializable;
import java.util.Date;

// 订单
public class Order implements Serializable {

    private static final long serialVersionUID = 1111L;

    private String orderId;
    private Date createTime;
    private Double price;
    private Integer status;
    private String userId;

    public Order() {
    }

    public Order(String orderId, Date createTime, Double price, Integer status, String userId) {
        this.orderId = orderId;
        this.createTime = createTime;
        this.price = price;
        this.status = status;
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                ", status=" + status +
                ", userId='" + userId + '\'' +
                '}';
    }
}
