package com.tutu.chifanme.beans;

/**
 * 订单实体类
 *
 * 作者：曹贵生 on 2016/12/13.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class Orders {

    private int id;
    private String order_name;
    private int price;
    private int uid;

    public Orders() {

    }

    public Orders(int id, String order_name, int price, int uid) {
        this.id = id;
        this.order_name = order_name;
        this.price = price;
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
