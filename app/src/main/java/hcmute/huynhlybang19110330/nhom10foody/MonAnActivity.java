package hcmute.huynhlybang19110330.nhom10foody;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hcmute.huynhlybang19110330.nhom10foody.adapter.FoodAdapter;
import hcmute.huynhlybang19110330.nhom10foody.adapter.RestaurantFoodAdapter;
import hcmute.huynhlybang19110330.nhom10foody.model.CartItem;
import hcmute.huynhlybang19110330.nhom10foody.model.CartModel;
import hcmute.huynhlybang19110330.nhom10foody.model.Food;
import hcmute.huynhlybang19110330.nhom10foody.model.Restaurant;

public class MonAnActivity extends AppCompatActivity {
    ImageView imgRes;
    TextView txtviewupperresname, txtviewresname;
    ListView lvfood;
    Button btncheckin;
    int resid;
    ArrayList<Food> arrFood;
    RestaurantFoodAdapter adapter;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_an);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AnhXa();
        resid=getIntent().getIntExtra("restaurantid",0);
        database = new Database(this);
        arrFood = new ArrayList<>();
        adapter = new RestaurantFoodAdapter(this,R.layout.foodmainpage_listview_line,arrFood);
        lvfood.setAdapter(adapter);
        getdatafood();
        Restaurant res = database.getrestaurantbyrid(resid);
        txtviewresname.setText(res.getTenNhaHang());
        txtviewupperresname.setText(res.getTenNhaHang());
        byte[] img1= res.getImage();
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(img1,0,img1.length);
        imgRes.setImageBitmap(bitmap1);
        ImageView btnback= (ImageView) findViewById(R.id.button_monan_back);
        btncheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MonAnActivity.this,Explore_Map.class);
                intent.putExtra("resid",res.getId());
                startActivity(intent);
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moMain();
            }
        });

//        //get the spinner from the xml.
//        Spinner dropdown = findViewById(R.id.button_monan_dropdown);
////create a list of items for the spinner.
//        String[] items = new String[]{"Báo Lỗi", "Bật Thông Báo", "Donate ủng hộ"};
////create an adapter to describe how the items are displayed, adapters are used in several places in android.
////There are multiple variations of this, but this is the basic variant.
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
////set the spinners adapter to the previously created one.
//
//        dropdown.setAdapter(adapter);
//    }

        Spinner spinner = (Spinner) findViewById(R.id.button_monan_dropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

    }
    public void moMain(){
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }
    public void AnhXa(){
        lvfood = (ListView) findViewById(R.id.listview_monan_datnhieu);
        imgRes = (ImageView) findViewById(R.id.imgview_monan_mainimg);
        txtviewresname = (TextView) findViewById(R.id.textview_monan_tennhahang);
        txtviewupperresname = (TextView) findViewById(R.id.textview_monan_tennhahangupper);
        btncheckin = (Button) findViewById(R.id.button_monan_checkin);
    }
    public void getdatafood()
    {
        Cursor datafood = database.getallrestaurantfood(resid);
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
                    Toast.makeText(MonAnActivity.this, "Please login to add food to cart!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MonAnActivity.this,Login.class);
                    startActivity(intent);
                }
                else
                {
                    int cid = database.getavailablecart(Global.getId());
                    if (cid == 0) {
                        if (database.insertcart(Global.getId(), 0) == false)
                        {
                            Toast.makeText(MonAnActivity.this, "Add cart false!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    cid = database.getavailablecart(Global.getId());
                    CartItem cartItem = database.getcartitembyfid(cid, fid);
                    if (cartItem != null) {
                        cartItem.setQuantity(cartItem.getQuantity() + 1);
                        if (database.updatecartitem(cartItem.getId(), cartItem.getCartid(), cartItem.getFood().getId(), cartItem.getQuantity())) {
                            Intent intent = new Intent(MonAnActivity.this, Cart.class);
                            intent.putExtra("cartid", cid);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MonAnActivity.this, "Something wrong when update", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        CartModel mcart = database.getcartbycid(cid);
                        if (mcart.getRestaurant()==null) {
                            if (database.insertcartitem(cid, fid, 1)) {
                                database.updatecartresid(cid,food.getIdNhaHang());
                                Intent intent = new Intent(MonAnActivity.this, Cart.class);
                                intent.putExtra("cartid", cid);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MonAnActivity.this, "Something wrong when insert", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            if (food.getIdNhaHang()!= mcart.getRestaurant().getId())
                            {
                                AlertDialog.Builder dialogdel = new AlertDialog.Builder(MonAnActivity.this);
                                dialogdel.setMessage("Bạn đang thêm một món ăn của một nhà hàng khác. Tiếp tục sẽ xóa giỏ hàng cũ của bạn !!");
                                dialogdel.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        int cid = database.getavailablecart(Global.getId());
                                        if (database.removeallcartitem(cid)==false)
                                        {
                                            Toast.makeText(MonAnActivity.this, "Remove cart failed!!!", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {

                                            if (database.insertcartitem(cid, fid, 1)) {
                                                database.updatecartresid(cid,food.getIdNhaHang());
                                                Intent intent = new Intent(MonAnActivity.this, Cart.class);
                                                intent.putExtra("cartid", cid);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                Toast.makeText(MonAnActivity.this, "Add item fail!!!", Toast.LENGTH_SHORT).show();
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
                                    Intent intent = new Intent(MonAnActivity.this,Cart.class);
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
}