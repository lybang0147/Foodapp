package hcmute.huynhlybang19110330.nhom10foody.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hcmute.huynhlybang19110330.nhom10foody.Database;
import hcmute.huynhlybang19110330.nhom10foody.OrderActivity;
import hcmute.huynhlybang19110330.nhom10foody.R;
import hcmute.huynhlybang19110330.nhom10foody.model.CartItem;
import hcmute.huynhlybang19110330.nhom10foody.model.Order;

public class OrderAdapter extends BaseAdapter {
    private OrderActivity context;
    private int layout;
    private List<CartItem> cartitemlist;
    private Database database;

    public OrderAdapter(OrderActivity context, int layout, List<CartItem> cartitemlist) {
        this.context = context;
        this.layout = layout;
        this.cartitemlist = cartitemlist;
    }
    private class ViewHolder{
        TextView txtviewquantity,txtviewfoodname,txtviewtotalprice;
    }
    @Override
    public int getCount() {
        return cartitemlist.size();
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
            holder.txtviewquantity = (TextView) view.findViewById(R.id.textview_orderline_soluong);
            holder.txtviewfoodname = (TextView) view.findViewById(R.id.textview_orderline_namefood);
            holder.txtviewtotalprice = (TextView) view.findViewById(R.id.textview_orderline_baseprice);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        CartItem cartItem = cartitemlist.get(i);
        holder.txtviewquantity.setText("x" + String.valueOf(cartItem.getQuantity()));
        holder.txtviewfoodname.setText(cartItem.getFood().getTenMon());
        holder.txtviewtotalprice.setText(String.valueOf(cartItem.getFood().getGia()*cartItem.getQuantity())+"đ");
        return view;
    }
}
