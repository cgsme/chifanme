package com.tutu.chifanme.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 *
 * 作者：曹贵生 on 2016/12/5.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chifanme.db";
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 用户表
        db.execSQL("CREATE TABLE IF NOT EXISTS person" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "phone VARCHAR, " +
                "password VARCHAR)");

        // 商家信息表
        db.execSQL("CREATE TABLE IF NOT EXISTS business" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR, phone VARCHAR," +
                "address VARCHAR," +
                " rating INTEGER)");

        // 菜单表    type_id: 菜单类别id
        db.execSQL("CREATE TABLE IF NOT EXISTS menus" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "business_id INTEGER, " +
                "type_id INTEGER, " +
                "type_name VARCHAR," +
                "menu_name VARCHAR," +
                "price INTEGER, " +
                "desc VARCHAR," +
                "evaluate VARCHAR)");

        // 订单表
        db.execSQL("CREATE TABLE IF NOT EXISTS orders" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "order_name VARCHAR," +
                "price INTEGER," +
                "uid INTEGER, " +
                "b_name VARCHAR)");

        // 菜单类别表   business_id: 商家id
        db.execSQL("CREATE TABLE IF NOT EXISTS order_type" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "business_id INTEGER, " +
                "type_name VARCHAR)");

        // 向商家信息表中插入数据
        db.execSQL("INSERT INTO business VALUES(null, ?, ?, ?, ?)", new Object[]{"第一食堂", "15659198516", "闽江学院一区", 1});
        db.execSQL("INSERT INTO business VALUES(null, ?, ?, ?, ?)", new Object[]{"第二食堂", "15659198516", "闽江学院二区", 2});
        db.execSQL("INSERT INTO business VALUES(null, ?, ?, ?, ?)", new Object[]{"第三食堂", "15659198516", "闽江学院三区", 3});
        db.execSQL("INSERT INTO business VALUES(null, ?, ?, ?, ?)", new Object[]{"第四食堂", "15659198516", "闽江学院四区", 4});

        // 向菜单类别表中插入数据
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 1, "热销" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 1, "折扣" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 1, "超值" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 1, "饮料" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 1, "草料" });

        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 2, "港式茶饮" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 2, "手做茶点" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 2, "港式饮品" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 2, "港式鲜饮" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 2, "欧式鲜饮" });

        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 3, "中式简餐" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 3, "西式简餐" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 3, "意大利面" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 3, "鲜奶热饮" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 3, "鲜奶冷饮" });

        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 4, "火锅套餐" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 4, "鲜饮系列" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 4, "烧烤系列" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 4, "金牌烤羊" });
        db.execSQL("INSERT INTO order_type VALUES(null, ?, ?)", new Object[]{ 4, "铜牌烤羊" });

        // 向菜单表中插入数据
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 1, "热销", "丝袜奶茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 1, "热销", "臭袜奶茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 1, "热销", "饭团奶茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 1, "热销", "菜团奶茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 1, "热销", "西瓜奶茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 1, "热销", "冬瓜奶茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 1, "热销", "男瓜奶茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 1, "热销", "棉裤秀奶", Math.random()*100, "好喝的哦...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 2, "优惠套餐", "皮衣红茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 2, "优惠套餐", "布衣红茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 2, "优惠套餐", "皮裤黑茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 2, "优惠套餐", "冰红茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 2, "优惠套餐", "热红茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 2, "优惠套餐", "热黄茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 2, "优惠套餐", "冰黄茶", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 2, "优惠套餐", "热蓝茶", Math.random()*100, "好喝的哦...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 3, "台式小吃", "大块豆腐", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 3, "台式小吃", "不辣烫", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 3, "台式小吃", "麻辣烫", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 3, "台式小吃", "烧大块", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 3, "台式小吃", "红烧鱼", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 3, "台式小吃", "黄烧鱼", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 3, "台式小吃", "炸地瓜", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 3, "台式小吃", "炸天瓜", Math.random()*100, "好喝的哦...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 4, "北方特色", "原味鸭蛋仔", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 4, "北方特色", "臭味狗蛋仔", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 4, "北方特色", "大饼干", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 4, "北方特色", "小饼干", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 4, "北方特色", "小面条", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 4, "北方特色", "小麦", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 4, "北方特色", "大麦", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 1, 4, "北方特色", "二锅头", Math.random()*100, "好喝的哦...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 1, "热销", "香酥鸡排饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 1, "热销", "福州香肠", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 1, "热销", "泉州香肠", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 1, "热销", "背景香肠", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 1, "热销", "广州香肠", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 1, "热销", "窄州香肠", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 1, "热销", "如本香肠", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 1, "热销", "像本香肠", Math.random()*100, "好啊吃的的啊...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 2, "热评", "鸡排靠面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 2, "热评", "猪排靠面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 2, "热评", "大白米饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 2, "热评", "小白米饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 2, "热评", "红烧肘子", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 2, "热评", "燕京啤酒", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 2, "热评", "南京啤酒", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 2, "热评", "小炒米饭", Math.random()*100, "好啊吃的的啊...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 3, "果物", "蜜汁肥牛饭", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 3, "果物", "汤汁肥牛饭", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 3, "果物", "炒饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 3, "果物", "香炒饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 3, "果物", "吃炒饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 3, "果物", "pisa", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 3, "果物", "cookie", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 3, "果物", "flower", Math.random()*100, "好啊吃的的啊...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 4, "路面", "菜酱意面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 4, "路面", "肉酱意面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 4, "路面", "不脆辣条", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 4, "路面", "瓜子", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 4, "路面", "香脆薯条", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 4, "路面", "苹果", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 4, "路面", "榴莲", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 2, 4, "路面", "太阳果", Math.random()*100, "好啊吃的的啊...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 1, "水煮系列", "香鸡排饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 1, "水煮系列", "臭鸡排饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 1, "水煮系列", "大香肠", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 1, "水煮系列", "小香肠", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 1, "水煮系列", "猪肠子", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 1, "水煮系列", "鸡腿子", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 1, "水煮系列", "猪腿子", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 1, "水煮系列", "狗腿子肉", Math.random()*100, "好啊吃的的啊...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 2, "折扣", "羊角", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 2, "折扣", "羊肉", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 2, "折扣", "牛肉面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 2, "折扣", "鸡排靠面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 2, "折扣", "鸡排", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 2, "折扣", "鸡靠面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 2, "折扣", "大饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 2, "折扣", "大白饭", Math.random()*100, "好啊吃的的啊...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 3, "单点区", "肥牛饭", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 3, "单点区", "蜜汁饭", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 3, "单点区", "厨房炒饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 3, "单点区", "房间炒饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 3, "单点区", "吃饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 3, "单点区", "测所炒饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 3, "单点区", "meat", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 3, "单点区", "cow", Math.random()*100, "好啊吃的的啊...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 4, "考神套餐", "肉酱意面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 4, "考神套餐", "就是意面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 4, "考神套餐", "香条", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 4, "考神套餐", "香脆条", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 4, "考神套餐", "臭脆薯条", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 4, "考神套餐", "鸡爪子", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 4, "考神套餐", "狗腿子", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 3, 4, "考神套餐", "狗肉", Math.random()*100, "好啊吃的的啊...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 1, "配菜", "这是上面饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 1, "配菜", "下面饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 1, "配菜", "套餐饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 1, "配菜", "香炉鸡腿饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 1, "配菜", "蜜汁手枪腿", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 1, "配菜", "蜜汁鸡", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 1, "配菜", "香酥鸡", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 1, "配菜", "没鸡了", Math.random()*100, "好啊吃的的啊...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 2, "新品上市", "鸡排靠面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 2, "新品上市", "辣面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 2, "新品上市", "电饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 2, "新品上市", "甜米饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 2, "新品上市", "大甜饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 2, "新品上市", "小吃米饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 2, "新品上市", "效米饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 2, "新品上市", "大猪肉饭", Math.random()*100, "好啊吃的的啊...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 3, "调料", "牛饭", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 3, "调料", "猪饭", Math.random()*100, "好喝的哦...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 3, "调料", "狗饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 3, "调料", "猪炒饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 3, "调料", "牛炒饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 3, "调料", "妓炒饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 3, "调料", "鸡公炒饭", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 3, "调料", "不吃炒饭", Math.random()*100, "好啊吃的的啊...", "" });

        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 4, "单品", "拉拉意面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 4, "单品", "特色意面", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 4, "单品", "特色薯条", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 4, "单品", "超级薯条", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 4, "单品", "c+", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 4, "单品", "蓝蓝", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 4, "单品", "想不出菜名", Math.random()*100, "好啊吃的的啊...", "" });
        db.execSQL("INSERT INTO menus VALUES(null, ?, ?, ?, ?, ?, ?, ?)", new Object[]{ 4, 4, "单品", "这是一道菜", Math.random()*100, "好啊吃的的啊...", "" });

       /* for (int i = 0; i<5; i++) {
            for (int j = 0; j<5; j++) {
                db.execSQL("INSERT INTO business VALUES(null, ?, ?)", new Object[]{ j, "15659198516" });
            }
        }*/

    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}