package hcmute.huynhlybang19110330.nhom10foody;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import hcmute.huynhlybang19110330.nhom10foody.model.AddressModel;
import hcmute.huynhlybang19110330.nhom10foody.model.CartItem;
import hcmute.huynhlybang19110330.nhom10foody.model.CartModel;
import hcmute.huynhlybang19110330.nhom10foody.model.Food;
import hcmute.huynhlybang19110330.nhom10foody.model.OrderModel;
import hcmute.huynhlybang19110330.nhom10foody.model.Restaurant;
import hcmute.huynhlybang19110330.nhom10foody.model.User;
import hcmute.huynhlybang19110330.nhom10foody.model.UserInfoModel;

public class Database extends SQLiteOpenHelper {
    static public final String DBNAME= "Cappo.db";
    static public final String DBLOCATION="/data/data/hcmute.huynhlybang19110330.nhom10foody/databases/";
    public Database(@Nullable Context context) {
        super(context, "Cappo.db", null, 9);
    }
    @Override
    public void onCreate(SQLiteDatabase MyDB) {

}
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop table if exists users");
        MyDB.execSQL("drop table if exists restaurant");
        MyDB.execSQL("drop table if exists food");
        MyDB.execSQL("drop table if exists cart");
        MyDB.execSQL("drop table if exists cartitem");
        MyDB.execSQL("drop table if exists orders");
        MyDB.execSQL("drop table if exists address");
        MyDB.execSQL("drop table if exists userinfo");
    }
    public void CreateTable()
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB = this.getWritableDatabase();
        MyDB.execSQL("create Table if not exists users(id INTEGER PRIMARY KEY AUTOINCREMENT, username varchar(100), password varchar(50),phone varchar(20))");
        MyDB.execSQL("create table if not exists address(id INTEGER PRIMARY KEY AUTOINCREMENT, lat Double,lng Double,fulladdress text)");
        MyDB.execSQL("Create Table if not exists restaurant(id INTEGER PRIMARY KEY AUTOINCREMENT,name varchar(100),addressid int,info text,image blob, userid INTEGER, rate integer, " +
                "foreign key (userid) references users(id), foreign key (addressid) references address(id))");
        MyDB.execSQL("create Table if not exists food(id INTEGER PRIMARY KEY AUTOINCREMENT, restaurantid INTEGER, name varchar(100),price integer,image blob, " +
                "detail text,sellamount integer,status boolean,foreign key (restaurantid) references restaurant(id))");
        MyDB.execSQL("create Table if not exists cart(id INTEGER PRIMARY KEY AUTOINCREMENT,userid integer,restaurantid integer,total integer," +
                "foreign key (userid) references user(id), foreign key (restaurantid) references restaurant(id))");
        MyDB.execSQL("create Table if not exists cartitem(id INTEGER PRIMARY KEY AUTOINCREMENT, cartid integer, foodid integer,quantity integer," +
                "foreign key (cartid) references cart(id), foreign key (foodid) references food(id))");
        MyDB.execSQL("create Table if not exists orders(id INTEGER PRIMARY KEY AUTOINCREMENT, cartid integer,name varchar(100),resaddress integer,useraddress int, date text, payment text,shipprice integer,total integer," +
                "foreign key (cartid) references cart(id), foreign key (resaddress) references address(id),foreign key (resaddress) references address(id))");
        MyDB.execSQL("create Table if not exists userinfo(id INTEGER PRIMARY KEY AUTOINCREMENT, userid integer,name varchar(100), addressid integer, image blob," +
                "foreign key (userid) references user(id),foreign key (addressid) references address(id))");
    }
    //Users Table
    public Boolean insertUser(String username, String Password,String phone)
    {

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",Password);
        contentValues.put("phone",phone);
        long result = MyDB.insert("users",null,contentValues);
        if (result==-1) return false;
        else
            return true;
    }
    public Boolean checkusername(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username =?", new String[] {username});
        if (cursor.getCount()>0)
        {
            return true;
        }
        else
            return false;
    }
    public Cursor getuserbyusername(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username =?", new String[]{username});
        return cursor;
    }
    public Boolean checkusernamepassword(String username, String password)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = MyDB.rawQuery("Select * from users where username= ? and password= ?", new String[] {username,password});
        if (cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public User getuserbyuid(int userid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where id=?",new String[] {String.valueOf(userid)});
        while (cursor.moveToNext())
        {
            return new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        }
        return  null;
    }
    //Food table
    public Boolean insertfood(String name,int price,String detail,byte[] image,int restaurantid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",price);
        contentValues.put("detail",detail);
        contentValues.put("image",image);
        contentValues.put("sellamount",0);
        contentValues.put("status",1);
        contentValues.put("restaurantid",restaurantid);

        long result = MyDB.insert("food",null,contentValues);
        if (result==-1)
        {
            return  false;
        }
        else
            return true;
    }
    public Cursor getallfood()
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from food",null);
        return cursor;
    }
    public Food getfoodbyfid(int id)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from food where id = ?", new String[] {String.valueOf(id)});
        while(cursor.moveToNext())
        return new Food(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getBlob(4),
                cursor.getString(5),
                cursor.getInt(6),
                (Boolean) (cursor.getInt(7)==1));
        return null;
    }
    public boolean updateFood(int id, String name,String price, String detail, byte[] image)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("price",price);
        contentValues.put("detail",detail);
        contentValues.put("image",image);
        long result = MyDB.update("food",contentValues,"id = ?",new String[]{String.valueOf(id)});
        if (result==-1)
            return false;
        else
            return true;
    }
    public boolean deleteFood(int id)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        long result = MyDB.delete("food","id=?",new String[] {String.valueOf(id)});
        if (result==-1)
            return false;
        else
            return true;
    }
    public ArrayList<Food> searchfood(String name)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ArrayList<Food> arrfood = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery("Select * from food where name like '%" + name +"%'",null);
        while (cursor.moveToNext())
        {
            arrfood.add(new Food(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getBlob(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    (Boolean) (cursor.getInt(7)==1)));
        }
        return arrfood;
    }
    //Restaurant table
    public boolean insertRestaurant(String tennhahang, int diachi,String mota,byte[] image, int userid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", tennhahang);
        contentValues.put("addressid", diachi);
        contentValues.put("info",mota);
        contentValues.put("image",image);
        contentValues.put("userid",userid);
        contentValues.put("rate",0);
        long result = MyDB.insert("restaurant",null,contentValues);
        if (result==-1)
        {
            return  false;
        }
        else
            return true;
    }
    public Cursor getrestaurantfood(int userid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select food.id,food.name,price,food.image,sellamount,status from users inner join restaurant on users.id=restaurant.userid inner join food on food.restaurantid=restaurant.id where users.id=?", new String[] {String.valueOf(userid)});
        return cursor;
    }
    public Cursor getallrestaurantfood(int restaurantid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from food where restaurantid=?",new String[] {String.valueOf(restaurantid)});
        return cursor;
    }

    public boolean checkrestaurant(int userid)
    {
        SQLiteDatabase MyDb = this.getWritableDatabase();
        Cursor cursor = MyDb.rawQuery("Select * from users inner join restaurant on ?=restaurant.userid",new String[] {String.valueOf(userid)});
        if (cursor.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public ArrayList<Restaurant> searchres(String name)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * from restaurant where name like '%"+name +"%'",null);
        ArrayList<Restaurant> arrres = new ArrayList<>();
        while (cursor.moveToNext())
        {
            arrres.add(new Restaurant(
                    cursor.getInt(0),
                    cursor.getString(1),
                    this.getaddressbyid(cursor.getInt(2)),
                    cursor.getString(3),
                    cursor.getBlob(4),
                    cursor.getInt(5),
                    cursor.getFloat(6)));
        }
        return arrres;
    }
    public Cursor getallrestaurant()
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from restaurant",null);
        return cursor;
    }
    public Restaurant getrestaurantbyuid(int userid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = MyDB.rawQuery("Select restaurant.id,name,addressid,info,image,userid,rate from users inner join restaurant on ?=restaurant.userid",new String[] {String.valueOf(userid)});
        while (cursor.moveToNext())
        {
            return new Restaurant(cursor.getInt(0),
                    cursor.getString(1),
                    this.getaddressbyid(cursor.getInt(2)),
                    cursor.getString(3),
                    cursor.getBlob(4),
                    cursor.getInt(5),
                    cursor.getFloat(6));
        }
        return null;
    }
    public Restaurant getrestaurantbyrid(int restaurantid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from restaurant where id=?",new String[] {String.valueOf(restaurantid)});
        while (cursor.moveToNext())
        {
            return new Restaurant(cursor.getInt(0),
                    cursor.getString(1),
                    this.getaddressbyid(cursor.getInt(2)),
                    cursor.getString(3),
                    cursor.getBlob(4),
                    cursor.getInt(5),
                    cursor.getFloat(6));
        }
        return null;
    }
    //Cart
    public boolean insertcart(int uid,int total)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid",uid);
        contentValues.put("total",total);
        long result = MyDB.insert("cart",null,contentValues);
        if (result==-1)
        {
            return  false;
        }
        else
            return true;
    }
    public int getavailablecart(int userid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from cart where id not in (Select cart.id from cart inner join orders where cart.id=orders.cartid) and userid = ?", new String[] {String.valueOf(userid)});
        if (cursor.getCount()==0)
        {
            return 0;
        }
        else
            while (cursor.moveToNext()) {
                return cursor.getInt(0);
            }
            return 0;
    }
    public CartModel getcartbycid(int cartid) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from cart where id=?", new String[]{String.valueOf(cartid)});
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.isNull(2)) {
                    return new CartModel(
                            cursor.getInt(0),
                            this.getuserbyuid(cursor.getInt(1)),
                            null,
                            cursor.getInt(3)
                    );
                } else {
                    return new CartModel(
                            cursor.getInt(0),
                            this.getuserbyuid(cursor.getInt(1)),
                            this.getrestaurantbyrid(cursor.getInt(2)),
                            cursor.getInt(3));
                }
            }

        }
        return null;
    }
    public boolean updatecartresid(int cartid,int resid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("restaurantid",resid);
        long result = MyDB.update("cart",contentValues,"id=?",new String[] {String.valueOf(cartid)});
        if (result==-1)
        {
            return false;
        }
        else
            return true;
    }
    public boolean updatecartprice(int cartid,int price)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("total",price);
        long result = MyDB.update("cart",contentValues,"id=?",new String[]{String.valueOf(cartid)});
        if (result==-1)
        {
            return  false;
        }
        else
            return true;
    }
    //CartItem
    public Cursor getcartitem(int cartid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ArrayList<CartItem> cartItems = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery("Select * from cartitem where cartid=?",new String[] {String.valueOf(cartid)});

        return cursor;
    }
    public CartItem getcartitembyfid(int cartid,int fid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from cartitem where cartid=? and foodid=?",new String[] {String.valueOf(cartid),String.valueOf(fid)});
        while (cursor.moveToNext())
        {
            return new CartItem(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    this.getfoodbyfid(cursor.getInt(2)),
                    cursor.getInt(3)
            );
        }
        return null;
    }
    public boolean insertcartitem(int cartid,int foodid,int quantity)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cartid",cartid);
        contentValues.put("foodid",foodid);
        contentValues.put("quantity",quantity);
        long result = MyDB.insert("cartitem",null,contentValues);
        if (result==-1)
        {
            return  false;
        }
        else
            return true;
    }
    public boolean updatecartitem(int id,int cartid,int foodid,int quantity)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cartid",cartid);
        contentValues.put("foodid",foodid);
        contentValues.put("quantity",quantity);
        long result = MyDB.update("cartitem",contentValues,"id=?",new String[] {String.valueOf(id)});
        if (result==-1)
            return false;
        else
            return true;
    }
    public boolean removecartitem(int id)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        long result = MyDB.delete("cartitem","id=?",new String[] {String.valueOf(id)});
        if (result==-1)
            return false;
        else
            return true;
    }
    public boolean removeallcartitem(int cartid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        long result = MyDB.delete("cartitem","cartid=?",new String[] {String.valueOf(cartid)});
        if (result==-1)
            return false;
        return true;
    }

    //address
    public boolean insertaddress(Double lat,Double lng,String fulladdress)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lat",lat);
        contentValues.put("lng",lng);
        contentValues.put("fulladdress",fulladdress);
        long result = MyDB.insert("address",null,contentValues);
        if (result==-1)
            return false;
            else
                return true;
    }
    public boolean updateaddress(int id,Double lat,Double lng,String fulladdress)
    {
        SQLiteDatabase MyDB= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lat",lat);
        contentValues.put("lng",lng);
        contentValues.put("fulladdress",fulladdress);
        long result = MyDB.update("address",contentValues,"id=?",new String[] {String.valueOf(id)});
        if (result==-1)
            return false;
        else
            return true;
    }
    public AddressModel getaddressbyid(int id)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from address where id=?",new String[] {String.valueOf(id)});
        while(cursor.moveToNext())
        {
            return new AddressModel(cursor.getInt(0),
                    cursor.getDouble(1),
                    cursor.getDouble(2),
                    cursor.getString(3));
        }
        return null;
    }
    public AddressModel getnewaddress()
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from address order by id desc limit 1",null);
        while(cursor.moveToNext())
        {
            return new AddressModel(cursor.getInt(0),
                    cursor.getDouble(1),
                    cursor.getDouble(2),
                    cursor.getString(3));
        }
        return null;
    }
    //userinfo
    public boolean insertuserinfo(int userid,String name,int addressid,byte[] image)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid",userid);
        contentValues.put("name",name);
        contentValues.put("addressid",addressid);
        contentValues.put("image",image);
        long result = MyDB.insert("userinfo",null,contentValues);
        if (result==-1)
        {
            return false;
        }
        else
            return true;
    }
    public boolean updateuserinfo(int id,int userid,String name,int addressid,byte[] image)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid",userid);
        contentValues.put("name",name);
        contentValues.put("addressid",addressid);
        contentValues.put("image",image);
        long result = MyDB.update("userinfo",contentValues,"id=?",new String[] {String.valueOf(id)});
        if (result==-1)
        {
            return false;
        }
        else
            return true;
    }
    public UserInfoModel getuserinfo(int userid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from userinfo where userid=?",new String[] {String.valueOf(userid)});
        while (cursor.moveToNext())
        {
           return new UserInfoModel(cursor.getInt(0),
                    this.getuserbyuid(cursor.getInt(1)),
                    cursor.getString(2),
                    this.getaddressbyid(cursor.getInt(3)),
                    cursor.getBlob(4));
        }
        return null;
    }
    public boolean addOrder(int cartid,String name,int resaddress,int useraddress,int ship,int total)
    {
        SQLiteDatabase MyDB =this.getWritableDatabase();
        Cursor newcur = MyDB.rawQuery("SELECT datetime('now','localtime')",null);
        newcur.moveToNext();
        String date = newcur.getString(0);
        ContentValues contentValues = new ContentValues();
        contentValues.put("cartid",cartid);
        contentValues.put("name",name);
        contentValues.put("resaddress",resaddress);
        contentValues.put("useraddress",useraddress);
        contentValues.put("date",date);
        contentValues.put("payment","Tiền mặt");
        contentValues.put("shipprice",ship);
        contentValues.put("total",total);
        long result = MyDB.insert("orders",null,contentValues);
        if (result==-1)
            return false;
        return true;
    }
    public ArrayList<OrderModel> getallorder(int userid)
    {
        ArrayList<OrderModel> listorder = new ArrayList<>();
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select orders.id,cartid,name,resaddress,useraddress,date,payment,shipprice,orders.total " +
                "from orders inner join cart on cart.id=orders.cartid where userid=? ",new String[] {String.valueOf(userid)});
        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                listorder.add(new OrderModel(cursor.getInt(0),
                        this.getcartbycid(cursor.getInt(1)),
                        cursor.getString(2),
                        this.getaddressbyid(cursor.getInt(3)),
                        this.getaddressbyid(cursor.getInt(4)),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getInt(8)));
            }
            return listorder;
        }
        return null;
    }
    public OrderModel getorderbyoid(int orderid)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from orders where id=?", new String[] {String.valueOf(orderid)});
        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                return new OrderModel(cursor.getInt(0),
                        this.getcartbycid(cursor.getInt(1)),
                        cursor.getString(2),
                        this.getaddressbyid(cursor.getInt(3)),
                        this.getaddressbyid(cursor.getInt(4)),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7),
                        cursor.getInt(8));
            }
        }
        return null;
    }

}
