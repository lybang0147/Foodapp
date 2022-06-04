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

import hcmute.huynhlybang19110330.nhom10foody.Database;
import hcmute.huynhlybang19110330.nhom10foody.MainActivity;
import hcmute.huynhlybang19110330.nhom10foody.MainPage;
import hcmute.huynhlybang19110330.nhom10foody.R;
import hcmute.huynhlybang19110330.nhom10foody.model.Food;
import hcmute.huynhlybang19110330.nhom10foody.model.Restaurant;

public class FoodAdapter extends BaseAdapter {
private MainPage context;
private int layout;
private List<Food> foodList;
private Database database;
    public FoodAdapter(MainPage context, int layout, List<Food> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
    }

    private class ViewHolder{
        ImageView imgviewFood1, imgviewFood2;
        TextView txtviewFoodName1, txtviewResName1, txtviewPrice1,txtviewFoodName2, txtviewResName2, txtviewPrice2;

    }
    @Override
    public int getCount()
    {
        if (foodList.size()%2==0)
        return foodList.size()/2;
        else
            return foodList.size()/2+1;
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
            holder.imgviewFood1=(ImageView) view.findViewById(R.id.imgview_mainpageline_firstimg);
            holder.imgviewFood2=(ImageView) view.findViewById(R.id.imgview_mainpageline_secondimg);
            holder.txtviewFoodName1 = (TextView) view.findViewById(R.id.txtview_mainpageline_firstfoodname);
            holder.txtviewFoodName2 = (TextView) view.findViewById(R.id.txtview_mainpageline_secondfoodname);
            holder.txtviewPrice1=(TextView) view.findViewById(R.id.txtview_mainpageline_firstprice);
            holder.txtviewPrice2=(TextView) view.findViewById(R.id.txtview_mainpageline_secondprice);
            holder.txtviewResName1 = (TextView) view.findViewById(R.id.txtview_mainpageline_firstresname);
            holder.txtviewResName2 = (TextView) view.findViewById(R.id.txtview_mainpageline_secondresname);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        if (i*2+1<foodList.size()) {
            Food food1 = foodList.get(i*2);
            Food food2 =foodList.get(i*2+1);
            byte[] img1= food1.getImage();
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(img1,0,img1.length);
            holder.imgviewFood1.setImageBitmap(bitmap1);
            holder.txtviewPrice1.setText(String.valueOf(food1.getGia())+"đ");
            holder.txtviewFoodName1.setText(food1.getTenMon());
            Restaurant res1 = database.getrestaurantbyrid(food1.getIdNhaHang());
            holder.txtviewResName1.setText(res1.getTenNhaHang());
            byte[] img2= food2.getImage();
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(img2,0,img2.length);
            holder.imgviewFood2.setImageBitmap(bitmap2);
            holder.txtviewPrice2.setText(String.valueOf(food2.getGia()) +"đ");
            holder.txtviewFoodName2.setText(food2.getTenMon());
            Restaurant res2 = database.getrestaurantbyrid(food2.getIdNhaHang());
            holder.txtviewResName2.setText(res2.getTenNhaHang());
            holder.imgviewFood1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.Dialogaddtocart(food1.getId());
                }
            });
            holder.imgviewFood2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.Dialogaddtocart(food2.getId());
                }
            });
        }
        else
        {
            Food food1 = foodList.get(i*2);
            byte[] img1= food1.getImage();
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(img1,0,img1.length);
            holder.imgviewFood1.setImageBitmap(bitmap1);
            holder.txtviewPrice1.setText(String.valueOf(food1.getGia())+"đ");
            holder.txtviewFoodName1.setText(food1.getTenMon());
            Restaurant res1 = database.getrestaurantbyrid(food1.getIdNhaHang());
            holder.txtviewResName1.setText(res1.getTenNhaHang());
            holder.imgviewFood2.setImageBitmap(null);
            holder.txtviewFoodName2.setText("");
            holder.txtviewPrice2.setText("");
            holder.txtviewResName2.setText("");
            holder.imgviewFood1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.Dialogaddtocart(food1.getId());
                }
            });
        }
        return view;
    }
}
