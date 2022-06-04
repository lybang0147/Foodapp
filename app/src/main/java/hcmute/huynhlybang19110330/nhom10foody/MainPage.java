package hcmute.huynhlybang19110330.nhom10foody;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hcmute.huynhlybang19110330.nhom10foody.adapter.FoodAdapter;
import hcmute.huynhlybang19110330.nhom10foody.adapter.RestaurantAdapter;
import hcmute.huynhlybang19110330.nhom10foody.adapter.RestaurantFoodAdapter;
import hcmute.huynhlybang19110330.nhom10foody.model.CartItem;
import hcmute.huynhlybang19110330.nhom10foody.model.CartModel;
import hcmute.huynhlybang19110330.nhom10foody.model.Food;
import hcmute.huynhlybang19110330.nhom10foody.model.OrderModel;
import hcmute.huynhlybang19110330.nhom10foody.model.Restaurant;

public class MainPage extends AppCompatActivity {
    EditText search;
    ListView lvfood;
    int flag;
    ArrayList<Food> arrFood;
    ArrayList<Restaurant> arrRes;
    FoodAdapter adapter;
    RestaurantAdapter resadapter;
    ImageView imgviewcart;
    TextView txtviewsoluong;
    Button btnexplore,btnsearch,btnhome,btnorder,btnperson,btndeli,btnfood,btnrest,btnmonan;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        AnhXa();
        flag=0;
        database = new Database(this);
        lvfood = (ListView) findViewById(R.id.listview_trangchinh_sanpham);
        arrFood = new ArrayList<>();
        arrRes = new ArrayList<>();
        resadapter = new RestaurantAdapter(this,R.layout.mainpage_listview_line,arrRes);
        adapter = new FoodAdapter(this,R.layout.mainpage_listview_line,arrFood);
        btnfood.setVisibility(View.INVISIBLE);
        lvfood.setAdapter(adapter);
        getdatafood();
        setcartquantity();
//        btnfood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MainPage.this,MonAnActivity.class);
//                intent.putExtra("restaurantid",1);
//                startActivity(intent);
//            }
//        });
        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.getId()==-1)
                {
                    Toast.makeText(MainPage.this, "Please login to view order history!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainPage.this,Login.class);
                    startActivity(intent);
                }
                else
                {
                    ArrayList<OrderModel> arrorder = database.getallorder(Global.getId());
                    if (arrorder==null) {
                        Toast.makeText(MainPage.this, "Order Empty!!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(MainPage.this, Orderlist.class);
                        startActivity(intent);
                    }
                }
            }
        });
        btnperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this,Person.class);
                intent.putExtra("status",1);
                startActivity(intent);
            }
        });
        btnrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            lvfood.setAdapter(resadapter);
            getdatares();
            btnrest.setVisibility(View.INVISIBLE);
            btnfood.setVisibility(View.VISIBLE);
            flag=1;
            }
        });
        btnfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvfood.setAdapter(adapter);
                btnfood.setVisibility(View.INVISIBLE);
                btnrest.setVisibility(View.VISIBLE);
                flag=0;
                getdatafood();
            }
        });
        imgviewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.getId() == -1)
                {
                    Toast.makeText(MainPage.this, "Please login to view cart!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainPage.this,Login.class);
                    startActivity(intent);
                }
                else
                {
                    int cid = database.getavailablecart(Global.getId());
                    if (cid == 0) {
                        if (database.insertcart(Global.getId(), 0)) ;
                    }
                    cid = database.getavailablecart(Global.getId());
                    Intent intent = new Intent(MainPage.this, Cart.class);
                    intent.putExtra("cartid", cid);
                    startActivity(intent);
                }
            }
        });
        btnexplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this,Explore_Map.class);
                startActivity(intent);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length()!=0) {
                    if (flag==0)
                    {
                        arrFood.clear();
                        arrFood.addAll(database.searchfood(search.getText().toString().trim()));
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        arrRes.clear();
                        arrRes.addAll(database.searchres(search.getText().toString().trim()));
                        resadapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    if (flag==0) {
                        getdatafood();
                    }
                    else
                        getdatares();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void getdatares()
    {
        Cursor datares = database.getallrestaurant();
        arrRes.clear();
        while (datares.moveToNext())
        {
            arrRes.add( new Restaurant(
                    datares.getInt(0),
                    datares.getString(1),
                    database.getaddressbyid(datares.getInt(2)),
                    datares.getString(3),
                    datares.getBlob(4),
                    datares.getInt(5),
                    datares.getFloat(6)
            ));
        }
        resadapter.notifyDataSetChanged();
    }
    public void getdatafood()
    {
        int flag=1;
        Cursor datafood = database.getallfood();
        arrFood.clear();
        while (datafood.moveToNext())
        {
            arrFood.add(new Food(
                    datafood.getInt(0),
                    datafood.getInt(1),
                    datafood.getString(2),
                    datafood.getInt(3),
                    datafood.getBlob(4),
                    datafood.getString(5),
                    datafood.getInt(6),
                    (Boolean) (datafood.getInt(7)== 1)

            ));
        }
        adapter.notifyDataSetChanged();
    }
    public void AnhXa()
    {
        search = (EditText) findViewById(R.id.edittext_trangchinh_search);
        btndeli=(Button) findViewById(R.id.button_trangchinh_giaohang);
        btnexplore=(Button) findViewById(R.id.button_trangchinh_khampha);
        btnfood= (Button) findViewById(R.id.button_trangchinh_monan);
        btnrest = (Button) findViewById(R.id.button_trangchinh_nhahang);
        btnhome = (Button) findViewById(R.id.button_trangchinh_home);
        btnorder = (Button) findViewById(R.id.button_trangchinh_donhang);
        btnperson= (Button) findViewById(R.id.button_trangchinh_canhan);
        imgviewcart = (ImageView) findViewById(R.id.imgview_trangchinh_cart);
        txtviewsoluong = (TextView) findViewById(R.id.textview_trangchinh_soluong);
    }

    public void Dialogaddtocart(int fid)
    {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        Food food = database.getfoodbyfid(fid);
        dialogXoa.setMessage("Bạn có muốn thêm món ăn " + food.getTenMon() +" không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Global.getId() == -1)
                {
                    Toast.makeText(MainPage.this, "Please login to add food to cart!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainPage.this,Login.class);
                    startActivity(intent);
                }
                else
                {
                    int cid = database.getavailablecart(Global.getId());
                    if (cid == 0) {
                        if (database.insertcart(Global.getId(),0)==false)
                        {
                            Toast.makeText(MainPage.this, "Add cart false", Toast.LENGTH_SHORT).show();
                        }
                    }
                    cid = database.getavailablecart(Global.getId());
                    CartItem cartItem = database.getcartitembyfid(cid, fid);
                    if (cartItem != null) {
                        cartItem.setQuantity(cartItem.getQuantity() + 1);
                        if (database.updatecartitem(cartItem.getId(), cartItem.getCartid(), cartItem.getFood().getId(), cartItem.getQuantity())) {
                            Intent intent = new Intent(MainPage.this, Cart.class);
                            intent.putExtra("cartid", cid);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainPage.this, "Something wrong when update", Toast.LENGTH_SHORT).show();
                        }
                    } else
                    {
                        CartModel mcart = database.getcartbycid(cid);
                        if (mcart.getRestaurant()==null) {
                            if (database.insertcartitem(cid, fid, 1)) {
                                database.updatecartresid(cid,food.getIdNhaHang());
                                Intent intent = new Intent(MainPage.this, Cart.class);
                                intent.putExtra("cartid", cid);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainPage.this, "Something wrong when insert", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            if (food.getIdNhaHang()!= mcart.getRestaurant().getId())
                            {
                                AlertDialog.Builder dialogdel = new AlertDialog.Builder(MainPage.this);
                                dialogdel.setMessage("Bạn đang thêm một món ăn của một nhà hàng khác. Tiếp tục sẽ xóa giỏ hàng cũ của bạn !!");
                                dialogdel.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        int cid = database.getavailablecart(Global.getId());
                                        if (database.removeallcartitem(cid)==false)
                                        {
                                            Toast.makeText(MainPage.this, "Remove cart failed!!!", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {

                                            if (database.insertcartitem(cid, fid, 1)) {
                                                database.updatecartresid(cid,food.getIdNhaHang());
                                                Intent intent = new Intent(MainPage.this, Cart.class);
                                                intent.putExtra("cartid", cid);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                Toast.makeText(MainPage.this, "Add item fail!!!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                                dialogdel.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                dialogdel.show();
                            }
                            else
                            {
                                if (database.insertcartitem(cid, fid, 1))
                                {
                                    Intent intent = new Intent(MainPage.this,Cart.class);
                                    intent.putExtra("cartid", cid);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                }
            }
        });

        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogXoa.show();
    }
    public void setcartquantity()
    {
        if (Global.getId()==-1)
        {
            txtviewsoluong.setText("0");
        }
        else {
            int cid = database.getavailablecart(Global.getId());
            if (cid ==0)
            {
                txtviewsoluong.setText("0");
            }
            else
            {
                int total=0;
                Cursor cursor = database.getcartitem(cid);
                while (cursor.moveToNext())
                {
                    total+=cursor.getInt(3);
                }
                txtviewsoluong.setText(String.valueOf(total));
            }
        }
    }
}