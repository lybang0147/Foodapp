package hcmute.huynhlybang19110330.nhom10foody.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hcmute.huynhlybang19110330.nhom10foody.Cart;
import hcmute.huynhlybang19110330.nhom10foody.Database;
import hcmute.huynhlybang19110330.nhom10foody.R;
import hcmute.huynhlybang19110330.nhom10foody.model.CartItem;

public class CartAdapter extends BaseAdapter {
    private Cart context;
    private int layout;
    private List<CartItem> arrCartitem;
    private Database database;
    public CartAdapter(Cart context, int layout, List<CartItem> arrCartitem) {
        this.context = context;
        this.layout = layout;
        this.arrCartitem = arrCartitem;
    }
    private class ViewHolder
    {
        ImageView imgviewfood;
        TextView txtviewfoodname, txtviewfooddescription,txtviewfoodprice,txtviewadd,txtviewremove,txtviewquantity;
    }
    @Override
    public int getCount() {
        return arrCartitem.size();
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
            holder.imgviewfood = view.findViewById(R.id.imgview_cartline_foodimg);
            holder.txtviewfoodname = view.findViewById(R.id.txtview_cartline_foodname);
            holder.txtviewfooddescription = view.findViewById(R.id.txtview_cartline_fooddescription);
            holder.txtviewfoodprice = view.findViewById(R.id.txtview_cartline_foodprice);
            holder.txtviewadd = view.findViewById(R.id.txtview_cartline_add);
            holder.txtviewremove = view.findViewById(R.id.txtview_cartline_remove);
            holder.txtviewquantity = view.findViewById(R.id.txtview_cartline_amount);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        CartItem cartItem = arrCartitem.get(i);
        byte[] img1= cartItem.getFood().getImage();
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(img1,0,img1.length);
        holder.imgviewfood.setImageBitmap(bitmap1);
        holder.txtviewfoodname.setText(cartItem.getFood().getTenMon());
        holder.txtviewfooddescription.setText(cartItem.getFood().getChiTiet());
        holder.txtviewquantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.txtviewfoodprice.setText(String.valueOf(cartItem.getFood().getGia()));
        holder.txtviewadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.addquantity(cartItem);
            }
        });
        holder.txtviewremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.removequantity(cartItem);
            }
        });
        return view;
    }
}
