package com.tutu.chifanme.beans;

/**
 * 用户实体类
 *
 * 作者：曹贵生 on 2016/12/5.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class Person {

    private int id;
    private String phone;
    private String password;

    public Person() {

    }

    public Person(String phone, String password) {
        this.password = password;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
