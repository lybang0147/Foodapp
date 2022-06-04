package hcmute.huynhlybang19110330.nhom10foody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hcmute.huynhlybang19110330.nhom10foody.adapter.CartAdapter;
import hcmute.huynhlybang19110330.nhom10foody.model.CartItem;
import hcmute.huynhlybang19110330.nhom10foody.model.Food;
import hcmute.huynhlybang19110330.nhom10foody.model.UserInfoModel;

public class Cart extends AppCompatActivity {
    TextView txtviewTotal;
    ImageView imgviewBack;
    ListView lvcartitem;
    int cartid;
    ArrayList<CartItem> arrcartlist;
    CartAdapter adapter;
    Database database;
    Button btnOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        AnhXa();
        database=new Database(this);
        arrcartlist = new ArrayList<>();
        adapter = new CartAdapter(this,R.layout.cart_listview_line,arrcartlist);
        lvcartitem.setAdapter(adapter);
        cartid = getIntent().getIntExtra("cartid",0);
        getdatacartitem();
        imgviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this,MainPage.class);
                startActivity(intent);
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoModel info = database.getuserinfo(Global.getId());
                if (info ==null)
                {
                    Intent intent = new Intent(Cart.this, UpdateUserInfo.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(Cart.this, OrderActivity.class);
                    intent.putExtra("cartid", cartid);
                    startActivity(intent);
                }
            }
        });
    }
    public void AnhXa()
    {
        txtviewTotal = (TextView) findViewById(R.id.textview_giohang_tongtien);
        imgviewBack = (ImageView) findViewById(R.id.imgview_giohang_back);
        btnOrder = (Button) findViewById(R.id.button_giohang_thanhtoan);
        lvcartitem= (ListView) findViewById(R.id.listview_giohang_hanghoa);
    }
    public void getdatacartitem()
    {
        arrcartlist.clear();
        Cursor cursor = database.getcartitem(cartid);
        while (cursor.moveToNext())
        {
            arrcartlist.add(new CartItem(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    database.getfoodbyfid(cursor.getInt(2)),
                    cursor.getInt(3)
            ));
        }
        getcarttotal();
        adapter.notifyDataSetChanged();
    }
    public void getcarttotal()
    {
        int total=0;
        Cursor cursor = database.getcartitem(cartid);
        while(cursor.moveToNext())
        {
            Food food = database.getfoodbyfid(cursor.getInt(2));
            total+=food.getGia()*cursor.getInt(3);
        }
        txtviewTotal.setText(String.valueOf(total));
    }
    public void addquantity(CartItem cartItem)
    {
        int newquantity = cartItem.getQuantity()+1;
        if (database.updatecartitem(cartItem.getId(),cartItem.getCartid(),cartItem.getFood().getId(),newquantity))
        {
            getdatacartitem();
        }
        else
        {
            Toast.makeText(this, "Something Wrong!!", Toast.LENGTH_SHORT).show();
        }
    }
    public void removequantity(CartItem cartItem)
    {
        int newquantity = cartItem.getQuantity()-1;
        if (newquantity==0)
        {
            if (database.removecartitem(cartItem.getId()))
            {
                getdatacartitem();
            }
            else
                Toast.makeText(this, "Something Wrong!!", Toast.LENGTH_SHORT).show();
        }
        else {
            if (database.updatecartitem(cartItem.getId(), cartItem.getCartid(), cartItem.getFood().getId(), newquantity)) {
                getdatacartitem();
            } else {
                Toast.makeText(this, "Something Wrong!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}