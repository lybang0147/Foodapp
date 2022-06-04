package hcmute.huynhlybang19110330.nhom10foody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.huynhlybang19110330.nhom10foody.adapter.OrderAdapter;
import hcmute.huynhlybang19110330.nhom10foody.adapter.OrderDetailAdapter;
import hcmute.huynhlybang19110330.nhom10foody.adapter.OrderListAdapter;
import hcmute.huynhlybang19110330.nhom10foody.model.CartItem;
import hcmute.huynhlybang19110330.nhom10foody.model.CartModel;
import hcmute.huynhlybang19110330.nhom10foody.model.Food;
import hcmute.huynhlybang19110330.nhom10foody.model.OrderModel;
import hcmute.huynhlybang19110330.nhom10foody.model.UserInfoModel;

public class OrderDetail extends AppCompatActivity {
    ImageView imgviewback;
    ListView lvcartitem;
    double dist;
    int orderid,totalquantity;
    ArrayList<CartItem> arrcartitem;
    TextView txtresname,txtdate,txttotalquantity,txtcarttotal,txtdistance,txtshipprice,txtordertotal,txtpayment,txtname,txtphone,txtaddress;
    OrderDetailAdapter adapter;
    Database database = new Database(OrderDetail.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        AnhXa();
        orderid = getIntent().getIntExtra("orderid",0);
        arrcartitem = new ArrayList<>();
        adapter = new OrderDetailAdapter(this,R.layout.order_listview_line,arrcartitem);
        lvcartitem.setAdapter(adapter);
        getdataorder();
        OrderModel orderModel = database.getorderbyoid(orderid);
        CartModel cartModel = database.getcartbycid(orderModel.getCart().getId());
        UserInfoModel info = database.getuserinfo(Global.getId());
        distance(cartModel.getRestaurant().getAddress().getLat(),cartModel.getRestaurant().getAddress().getLng(),info.getAddress().getLat(),info.getAddress().getLng());
        txtresname.setText(orderModel.getCart().getRestaurant().getTenNhaHang());
        txtdate.setText(orderModel.getDate());
        txtdistance.setText(String.format("Phí ship: %.2f km",dist));
        txtshipprice.setText(String.valueOf(orderModel.getShipprice())+ "đ");
        txtordertotal.setText(String.valueOf(orderModel.getTotal())+"đ");
        txtcarttotal.setText(String.valueOf(orderModel.getCart().getTotal())+"đ");
        txtname.setText(orderModel.getName());
        txtphone.setText(Global.getPhone());
        txtaddress.setText(orderModel.getUseraddress().getFulladdress());
        imgviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetail.this,OrderDetail.class);
                startActivity(intent);
            }
        });
    }
    public void getcarttotal()
    {
        int total=0;
        OrderModel orderModel = database.getorderbyoid(orderid);
        Cursor cursor = database.getcartitem(orderModel.getCart().getId());
        while(cursor.moveToNext())
        {
            total+=cursor.getInt(3);
        }
        txttotalquantity.setText("Tạm tính (" + String.valueOf(total) +" món)");
    }
    private void getdataorder() {
        arrcartitem.clear();
        OrderModel orderModel = database.getorderbyoid(orderid);
        Cursor cursor = database.getcartitem(orderModel.getCart().getId());
        while (cursor.moveToNext())
        {
            arrcartitem.add(new CartItem(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    database.getfoodbyfid(cursor.getInt(2)),
                    cursor.getInt(3)
            ));
        }
        adapter.notifyDataSetChanged();
    }

    private void AnhXa() {
        txtresname = (TextView) findViewById(R.id.txtview_orderdetail_restaurantname);
        txtdate = (TextView) findViewById(R.id.txtview_orderdetail_datetime);
        txttotalquantity = (TextView) findViewById(R.id.txtview_orderdetail_totalquantity);
        txtcarttotal = (TextView) findViewById(R.id.txtview_orderdetail_carttotal);
        txtdistance = (TextView) findViewById(R.id.txtview_orderdetail_distance);
        txtshipprice = (TextView) findViewById(R.id.txtview_orderdetail_shiptotal);
        txtordertotal = (TextView) findViewById(R.id.txtview_orderdetail_ordertotal);
        txtpayment = (TextView) findViewById(R.id.txtview_orderdetail_payment);
        txtname = (TextView) findViewById(R.id.txtview_orderdetail_name);
        txtphone = (TextView) findViewById(R.id.txtview_orderdetail_phone);
        txtaddress = (TextView) findViewById(R.id.txtview_orderdetail_address);
        imgviewback = (ImageView) findViewById(R.id.imgview_orderdetail_back);
        lvcartitem = (ListView) findViewById(R.id.listview_orderdetail_foodlist);
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
    }

}