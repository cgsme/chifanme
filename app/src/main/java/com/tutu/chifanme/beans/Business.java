package com.tutu.chifanme.beans;

/**
 * 商家实体类
 *
 * 作者：曹贵生 on 2016/12/5.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class Business {

    private int _id;
    private String name;
    private String address;

    public Business() {
    }

    public Business(int _id, String address, String name) {
        this._id = _id;
        this.address = address;
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
