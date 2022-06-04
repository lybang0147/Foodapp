package hcmute.huynhlybang19110330.nhom10foody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import hcmute.huynhlybang19110330.nhom10foody.adapter.OrderListAdapter;
import hcmute.huynhlybang19110330.nhom10foody.model.OrderModel;

public class Orderlist extends AppCompatActivity {
    ArrayList<OrderModel> arrorder;
    OrderListAdapter adapter;
    ImageView imgviewback;
    Database database = new Database(this);
    ListView lvorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        AnhXa();
        arrorder = new ArrayList<>();
        adapter = new OrderListAdapter(this,R.layout.orderlist_listview_line,arrorder);
        imgviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Orderlist.this,MainPage.class);
                startActivity(intent);
            }
        });
        lvorder.setAdapter(adapter);
        getdataorder();
    }

    private void getdataorder() {
        arrorder.clear();
        arrorder.addAll(database.getallorder(Global.getId()));
        adapter.notifyDataSetChanged();
    }

    private void AnhXa() {
        lvorder = (ListView) findViewById(R.id.listview_orderlist_order);
        imgviewback = (ImageView) findViewById(R.id.imgview_orderlist_back);
    }
}