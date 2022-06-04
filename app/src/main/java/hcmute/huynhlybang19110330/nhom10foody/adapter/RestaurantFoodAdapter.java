package hcmute.huynhlybang19110330.nhom10foody.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hcmute.huynhlybang19110330.nhom10foody.Database;
import hcmute.huynhlybang19110330.nhom10foody.Global;
import hcmute.huynhlybang19110330.nhom10foody.MainPage;
import hcmute.huynhlybang19110330.nhom10foody.MonAnActivity;
import hcmute.huynhlybang19110330.nhom10foody.R;
import hcmute.huynhlybang19110330.nhom10foody.model.Food;

public class RestaurantFoodAdapter extends BaseAdapter {
    private MonAnActivity context;
    private int layout;
    private List<Food> foodList;
    private Database database;

    public RestaurantFoodAdapter(MonAnActivity context, int layout, List<Food> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
    }

    public class ViewHolder{
        ImageView imgviewfood,imgaddtocart;
        TextView txtviewfoodname,txtviewfooddes,txtviewfoodprice;
    }
    @Override
    public int getCount() {
        return foodList.size();
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
        if (view==null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.imgviewfood = (ImageView) view.findViewById(R.id.imgview_foodmainpageline_foodimg);
            holder.imgaddtocart = (ImageView) view.findViewById(R.id.imgview_foodmainpageline_addtocart);
            holder.txtviewfoodname = (TextView) view.findViewById(R.id.txtview_foodmainpageline_foodname);
            holder.txtviewfooddes = (TextView) view.findViewById(R.id.txtview_foodmainpageline_fooddescription);
            holder.txtviewfoodprice = (TextView) view.findViewById(R.id.txtview_foodmainpageline_foodprice);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        Food food = foodList.get(i);
        byte[] img1= food.getImage();
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(img1,0,img1.length);
        holder.imgviewfood.setImageBitmap(bitmap1);
        holder.txtviewfoodname.setText(food.getTenMon());
        holder.txtviewfooddes.setText(food.getChiTiet());
        holder.txtviewfoodprice.setText(String.valueOf(food.getGia())+"đ");
        holder.imgaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.Dialogaddtocart(food.getId());
            }
        });
        return view;
    }

}
