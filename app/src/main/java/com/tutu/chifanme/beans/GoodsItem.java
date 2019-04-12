package com.tutu.chifanme.beans;

import java.util.Random;

/**
 *  商品实体类
 *
 * 作者：曹贵生 on 2016/12/3.
 * 邮箱：1595143088@qq.com
 * 说明：
 */
public class GoodsItem {
    public int id;
    public int typeId;
    public int rating;
    public String name;
    public String typeName;
    public double price;
    public int bId;
    public int count;

    public GoodsItem(int id, double price, String name, int typeId, String typeName, int bId) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.typeId = typeId;
        this.typeName = typeName;
        this.bId = bId;
        rating = new Random().nextInt(5)+1;
    }

   /* private static ArrayList<GoodsItem> goodsList;
    private static ArrayList<GoodsItem> typeList;

    *//**
     * 初始化数据
     *//*
    private static void initData(String businessId) {

       *//* goodsList = new ArrayList<>();
        typeList = new ArrayList<>();
        GoodsItem item = null;
        //===================================================================

        Cursor menusCursor = MakeOrderFragment.initData(businessId);
        int i =0;
        while (menusCursor.moveToNext()) {
            int id = menusCursor.getInt(menusCursor.getColumnIndex("_id"));
            double price = menusCursor.getDouble(menusCursor.getColumnIndex("price"));
            String name = menusCursor.getString(menusCursor.getColumnIndex("menu_name"));
            int typeId = menusCursor.getInt(menusCursor.getColumnIndex("type_id"));
            String typeName = menusCursor.getString(menusCursor.getColumnIndex("type_name"));
            int bId = menusCursor.getInt(menusCursor.getColumnIndex("business_id"));
            item = new GoodsItem(id, price, name, typeId, typeName, bId);
            goodsList.add(item);
            while (i >= 4) {
                typeList.add(item);
                i=-1;
            }
            i++;
        }*//*

        //=====================================================================

        *//*for(int i=1;i<15;i++){
            for(int j=1;j<10;j++){
                item = new GoodsItem(100*i+j, Math.random()*100,"商品"+(100*i+j), i, "种类"+i, 1);
                goodsList.add(item);
            }
            typeList.add(item);
        }*//*
        *//*item = new GoodsItem(1, Math.random()*100, "烤鸭", 1, "热销", 1);
        goodsList.add(item);
        item = new GoodsItem(1, Math.random()*100, "烤鸭", 1, "热销", 1);
        goodsList.add(item);
        typeList.add(item);*//*

       *//* item = new GoodsItem(2, Math.random()*100, "烤鸭", 2, "热销");
        typeList.add(item);
        item = new GoodsItem(5, Math.random()*100, "烤鸭", 3, "热销");
        typeList.add(item);
        item = new GoodsItem(7, Math.random()*100, "烤鸭", 4, "热销");
        typeList.add(item);
        item = new GoodsItem(9, Math.random()*100, "烤鸭", 5, "热销");
        typeList.add(item);
        item = new GoodsItem(10, Math.random()*100, "烤鸭", 6, "热销");
        typeList.add(item);*//*
    }

    *//**
     * 获取商品列表
     * @return
     *//*
    public static ArrayList<GoodsItem> getGoodsList(String id) {
        if(goodsList==null){
            initData(id);
        }
        return goodsList;
    }

    *//**
     * 获取类别列表
     * @return
     *//*
    public static ArrayList<GoodsItem> getTypeList(String id) {
        if(typeList==null) {
            initData(id);
        }
        return typeList;
    }*/

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }
}
