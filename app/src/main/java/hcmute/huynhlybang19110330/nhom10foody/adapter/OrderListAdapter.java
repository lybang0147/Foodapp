package hcmute.huynhlybang19110330.nhom10foody.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.helper.widget.Layer;

import java.util.List;

import hcmute.huynhlybang19110330.nhom10foody.Database;
import hcmute.huynhlybang19110330.nhom10foody.OrderDetail;
import hcmute.huynhlybang19110330.nhom10foody.Orderlist;
import hcmute.huynhlybang19110330.nhom10foody.R;
import hcmute.huynhlybang19110330.nhom10foody.model.OrderModel;

public class OrderListAdapter extends BaseAdapter {
    private Orderlist context;
    private int layout;
    private List<OrderModel> orderlist;
    Database database ;
    public OrderListAdapter(Orderlist context, int layout, List<OrderModel> orderlist) {
        this.context = context;
        this.layout = layout;
        this.orderlist = orderlist;
    }
    private class ViewHolder{
        LinearLayout layout;
        TextView txtviewdate,txtviewresname,txtviewpayment;
    }
    @Override
    public int getCount() {
        return orderlist.size();
    }

    @Override
    public Object getItem(int i) {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        database = new Database(context);
        if (view == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.layout=(LinearLayout) view.findViewById(R.id.layout_orderlistline);
            holder.txtviewdate = (TextView) view.findViewById(R.id.txtview_orderlistline_date);
            holder.txtviewpayment = (TextView) view.findViewById(R.id.txtview_orderlistline_payment);
            holder.txtviewresname = (TextView) view.findViewById(R.id.txtview_orderlistline_resname);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        OrderModel orderModel = orderlist.get(i);
        holder.txtviewdate.setText(orderModel.getDate());
        holder.txtviewresname.setText(orderModel.getCart().getRestaurant().getTenNhaHang());
        int cid = orderModel.getCart().getId();
        Cursor cursor = database.getcartitem(cid);
        int totalquantity=0;
        while (cursor.moveToNext())
        {
            totalquantity+=cursor.getInt(3);
        }
        holder.txtviewpayment.setText(orderModel.getTotal() + " (" +orderModel.getPayment() + ") " + " - "+ totalquantity + " món"  );
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetail.class);
                intent.putExtra("orderid",orderModel.getId());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
