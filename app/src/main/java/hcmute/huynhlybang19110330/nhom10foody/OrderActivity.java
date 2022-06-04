package hcmute.huynhlybang19110330.nhom10foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import hcmute.huynhlybang19110330.nhom10foody.adapter.OrderAdapter;
import hcmute.huynhlybang19110330.nhom10foody.model.CartItem;
import hcmute.huynhlybang19110330.nhom10foody.model.CartModel;
import hcmute.huynhlybang19110330.nhom10foody.model.Food;
import hcmute.huynhlybang19110330.nhom10foody.model.UserInfoModel;

public class OrderActivity extends AppCompatActivity implements OnMapReadyCallback {
    ListView lvcartitem;
    ImageView imgviewback;
    int cartid;
    View mapfragment;
    GoogleMap mMap;
    TextView txtviewdirect,txtviewsuainfo,txtdistance,txtviewuserinfo,txtviewoldprice, txtviewship,txtviewnewtotal,txtviewtotalfinal,txtviewtotalquantity,txtvieworder,txttongsophan,txtmapdistance;
    ArrayList<CartItem> arrcartitem;
    OrderAdapter adapter;
    Database database;
    LatLng latLng;
    Double dist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_order);
        mapFragment.getMapAsync(this);
        AnhXa();
        cartid = getIntent().getIntExtra("cartid",0);
        database = new Database(this);
        arrcartitem = new ArrayList<>();
        adapter = new OrderAdapter(this,R.layout.order_listview_line,arrcartitem);
        lvcartitem.setAdapter(adapter);
        CartModel cartModel = database.getcartbycid(cartid);
        UserInfoModel info = database.getuserinfo(Global.getId());
        latLng=new LatLng(info.getAddress().getLat(),info.getAddress().getLng());
        distance(cartModel.getRestaurant().getAddress().getLat(),cartModel.getRestaurant().getAddress().getLng(),info.getAddress().getLat(),info.getAddress().getLng());
        txtviewuserinfo.setText("Đặt cho:" + info.getName() + "\n" + info.getAddress().getFulladdress() + "\n" + Global.getPhone());
        getdataorder();
        gettotal();
        txtviewdirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, MapsDirection.class);
                intent.putExtra("lat1",info.getAddress().getLat());
                intent.putExtra("lng1",info.getAddress().getLng());
                intent.putExtra("lat2",cartModel.getRestaurant().getAddress().getLat());
                intent.putExtra("lng2",cartModel.getRestaurant().getAddress().getLng());
                intent.putExtra("cartid",cartid);
                startActivity(intent);
            }
        });
        txtviewsuainfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoModel userInfo = database.getuserinfo(Global.getId());
                Intent intent = new Intent(OrderActivity.this,UpdateUserInfo.class);
                intent.putExtra("type",userInfo.getId());
                startActivity(intent);
            }
        });
        imgviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this,Cart.class);
                intent.putExtra("cartid",cartid);
                startActivity(intent);
            }
        });
        txtvieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalprice=0, totalquantity=0;
                Cursor cursor = database.getcartitem(cartid);
                CartModel cartModel = database.getcartbycid(cartid);
                UserInfoModel info = database.getuserinfo(Global.getId());
                Double shipprice = dist *5000;
                int ship = shipprice.intValue();
                while(cursor.moveToNext())
                {
                    Food food = database.getfoodbyfid(cursor.getInt(2));
                    totalprice+=food.getGia()*cursor.getInt(3);
                    totalquantity+=cursor.getInt(3);
                }

                if (database.addOrder(cartid,info.getName(),cartModel.getRestaurant().getAddress().getId(),info.getAddress().getId(),ship,totalprice+ship))
                {
                    AlertDialog.Builder dialogdel = new AlertDialog.Builder(OrderActivity.this);
                    database.updatecartprice(cartid,totalprice);
                    dialogdel.setMessage("Bạn đã đặt hàng thành công");
                    dialogdel.setPositiveButton("Tuyệt", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(OrderActivity.this,MainPage.class);
                            startActivity(intent);
                        }
                    });
                    dialogdel.show();
                }
                else
                    Toast.makeText(OrderActivity.this, "Something Wrong!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void AnhXa()
    {
        lvcartitem = (ListView) findViewById(R.id.listview_order_foodlist);
        txtviewoldprice = (TextView) findViewById(R.id.textview_order_oldprice);
        txtviewship = (TextView) findViewById(R.id.textview_order_phigiaohang);
        txtviewnewtotal = (TextView) findViewById(R.id.textview_order_newprice);
        txtviewtotalfinal = (TextView) findViewById(R.id.textview_order_totalprice);
        txtviewtotalquantity = (TextView) findViewById(R.id.textview_order_totalsoluong);
        txtvieworder = (TextView) findViewById(R.id.textview_order_datcho);
        txttongsophan = (TextView) findViewById(R.id.textview_order_sophan);
        txtviewuserinfo = (TextView) findViewById(R.id.textview_order_inforuser);
        txtmapdistance = (TextView) findViewById(R.id.textview_order_mapdistance);
        txtdistance = (TextView) findViewById(R.id.textview_order_distance);
        txtviewsuainfo = (TextView) findViewById(R.id.textview_order_suainfor);
        txtviewdirect = (TextView) findViewById(R.id.txtview_order_checkdirection);
        imgviewback = (ImageView) findViewById(R.id.imgview_order_back);
    }
    public void getdataorder()
    {
        Cursor cursor = database.getcartitem(cartid);
        if (cursor.getCount()==0)
        {
            Toast.makeText(this, "Please add some food to cart", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrderActivity.this,MainPage.class);
            startActivity(intent);
        }
        else
        {
            while (cursor.moveToNext())
            {
                arrcartitem.add(new CartItem(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        database.getfoodbyfid(cursor.getInt(2)),
                        cursor.getInt(3))
                );}
            adapter.notifyDataSetChanged();
        }
    }
    public void gettotal()
    {
        int totalprice=0, totalquantity=0;
        Cursor cursor = database.getcartitem(cartid);
        Double shipprice = dist *5000;
        int ship = shipprice.intValue();
        while(cursor.moveToNext())
        {
            Food food = database.getfoodbyfid(cursor.getInt(2));
            totalprice+=food.getGia()*cursor.getInt(3);
            totalquantity+=cursor.getInt(3);
        }
        txtviewtotalquantity.setText(String.valueOf(totalquantity)+ " phần");
        txtviewoldprice.setText(String.valueOf(totalprice)+"đ");
        txtviewtotalfinal.setText(String.valueOf(totalprice+ship)+"đ");
        txtviewnewtotal.setText(String.valueOf(totalprice+ship)+"đ");
        txttongsophan.setText("Tổng " + String.valueOf(totalquantity) + " phần" );
        txtviewship.setText(ship+"đ");
    }
    public void distance(double lat1,double long1,double lat2,double long2)
    {
        double longdiff=long2-long1;
        double radlat1=Math.toRadians(lat1);
        double radlat2=Math.toRadians(lat2);
        double radlngdiff=Math.toRadians(longdiff);
        dist=Math.sin(radlat1)*Math.sin(radlat2) + Math.cos(radlat1)*Math.cos(radlat2)*Math.cos(radlngdiff);
        dist=Math.acos(dist);
        dist=Math.toDegrees(dist);
        //khoảng cách đơn vị miles
        dist=dist*60*1.1515;
        //Đơn vị km;
        dist=dist*1.609344;
        txtmapdistance.setText(String.format("%.2f km",dist));
        txtdistance.setText(String.format("%.2f km",dist));
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions marker = new MarkerOptions().position(latLng);
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }
}