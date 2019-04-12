package com.tutu.chifanme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tutu.chifanme.beans.GoodsItem;
import com.tutu.chifanme.beans.Orders;
import com.tutu.chifanme.beans.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：曹贵生 on 2016/12/5.
 * 邮箱：1595143088@qq.com
 * 说明：
 */

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * 添加用户
     * @param persons
     */
    public void add(List<Person> persons) {
        db.beginTransaction();  //开始事务
        try {
            for (Person person : persons) {
                db.execSQL("INSERT INTO person VALUES(null, ?, ?)", new Object[]{person.getPhone(), person.getPassword()});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 更新用户
     * @param person
     */
    public void updateAge(Person person) {
        ContentValues cv = new ContentValues();
//        cv.put("age", person.age);
        db.update("person", cv, "name = ?", new String[]{person.getPhone()});
    }

    /**
     * 删除用户
     * @param person
     */
    public void deleteOldPerson(Person person) {
//        db.delete("person", "age >= ?", new String[]{String.valueOf(person.age)});
    }

    /**
     * 查询所有用户，返回list
     * @return List<Person>
     */
    public List<Person> query() {
        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Person person = new Person();
          /*  person._id = c.getInt(c.getColumnIndex("_id"));
            person.name = c.getString(c.getColumnIndex("name"));
            person.age = c.getInt(c.getColumnIndex("age"));
            person.info = c.getString(c.getColumnIndex("info"));*/
            persons.add(person);
        }
        c.close();
        return persons;
    }

    /**
     * 查询所有用户，返回cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM person", null);
        return c;
    }

    /**
     * 根据手机查询用户
     * @param phone
     * @return Cursor
     */
    public Cursor queryUserByPhone(String phone) {
        Cursor c = db.query("person", null, "phone like ?", new String[] {phone}, null, null, null);
        return c;
    }

    /**
     * 查询商家表的所有数据，返回Cursor
     * @return Cursor
     */
    public Cursor queryBusiness() {
        Cursor cursor = db.rawQuery("SELECT * FROM business", null);
        return cursor;
    }

    /**
     * 模糊查询商家表的所有数据，返回Cursor
     * @return Cursor
     */
    public Cursor queryBusinessByLike(String name) {
        Cursor cursor = db.query("business", null, "name like '%"+ name +"%'", null, null, null, null);
        return cursor;
    }

    /**
     * 根据id查询商家名称，返回Cursor
     * @return Cursor
     */
    public Cursor queryBusinessById(String id) {
        Cursor cursor = db.query("business", null, "_id = ?", new String[] {id}, null, null, null);
        return cursor;
    }

    /**
     * 查询菜单表的所有数据，返回Cursor
     * @return Cursor
     */
    public Cursor queryMenus(String businessId) {
        Cursor cursor = db.query("menus", null, "business_id = ?", new String[] {businessId}, null, null, null);
        return cursor;
    }

    /**
     * 模糊查询菜单表的所有数据，返回Cursor
     * @return Cursor "name like '%" + searcherFilter + "%'"
     */
    public Cursor queryMenusByLike(String name) {
        Cursor cursor = db.query("menus", null, "menu_name like '%"+ name +"%'", null, null, null, null);
        return cursor;
    }

    /**
     * 查询菜单类别表的所有数据，返回Cursor
     * @return Cursor
     */
    public Cursor queryMenusType(String businessId) {
        Cursor cursor = db.query("order_type", null, "business_id = ?", new String[] {businessId}, null, null, null);
        return cursor;
    }

    /**
     * 保存用户订单
     * @param ordersList
     * @param priceList
     * @param userId
     */
    public void saveOrders(ArrayList<String> ordersList, ArrayList<String> priceList, int userId, String bName) {
        db.beginTransaction();  //开始事务
        try {
            for (int i=0; i<ordersList.size(); i++) {
                db.execSQL("INSERT INTO orders VALUES(null, ?, ?, ?, ?)",
                        new Object[]{ordersList.get(i), priceList.get(i), userId, bName});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * 查询订单表的所有数据，返回Cursor
     * @return Cursor
     */
    public Cursor queryAllOrders(String uid) {
        Cursor cursor = db.query("orders", null, "uid = ?",
                new String[] { uid }, null, null, null);
        return cursor;
    }


    /**
     * 关闭数据库
     */
    public void closeDB() {
        db.close();
    }
}
