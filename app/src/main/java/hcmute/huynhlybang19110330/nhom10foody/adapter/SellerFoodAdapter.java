package hcmute.huynhlybang19110330.nhom10foody.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hcmute.huynhlybang19110330.nhom10foody.Database;
import hcmute.huynhlybang19110330.nhom10foody.R;
import hcmute.huynhlybang19110330.nhom10foody.SellerMainPage;
import hcmute.huynhlybang19110330.nhom10foody.model.Food;

public class SellerFoodAdapter extends BaseAdapter {
    private SellerMainPage context;
    private int layout;
    private List<Food> foodList;
    private Database database;
    public SellerFoodAdapter(SellerMainPage context, int layout, List<Food> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
    }
    
    public class ViewHolder
    {
        ImageView imgviewFood1, imgviewFood2;
        TextView txtviewFoodName1, txtviewSellAmount1, txtviewPrice1,txtviewFoodName2, txtviewSellAmount2, txtviewPrice2;
        Button btnedit1,btndel1,btnedit2,btndel2;
    }

    @Override
    public int getCount() {
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

        if (view == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.imgviewFood1=(ImageView) view.findViewById(R.id.imgview_sellermainpageline_firstimg);
            holder.imgviewFood2=(ImageView) view.findViewById(R.id.imgview_sellermainpageline_secondimg);
            holder.txtviewFoodName1 = (TextView) view.findViewById(R.id.txtview_sellermainpageline_firstfoodname);
            holder.txtviewFoodName2 = (TextView) view.findViewById(R.id.txtview_sellermainpageline_secondfoodname);
            holder.txtviewPrice1=(TextView) view.findViewById(R.id.txtview_sellermainpageline_firstprice);
            holder.txtviewPrice2=(TextView) view.findViewById(R.id.txtview_sellermainpageline_secondprice);
            holder.txtviewSellAmount1 = (TextView) view.findViewById(R.id.txtview_sellermainpageline_firstsellamount);
            holder.txtviewSellAmount2 = (TextView) view.findViewById(R.id.txtview_sellermainpageline_secondsellamount);
            holder.btnedit1= (Button) view.findViewById(R.id.btn_sellermainpageline_firstedit);
            holder.btndel1=(Button) view.findViewById(R.id.btn_sellermainpageline_firstdelete);
            holder.btndel2=(Button) view.findViewById(R.id.btn_sellermainpageline_seconddelete);
            holder.btnedit2=(Button) view.findViewById(R.id.btn_sellermainpageline_secondedit);
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
            holder.txtviewPrice1.setText(String.valueOf(food1.getGia()));
            holder.txtviewFoodName1.setText(food1.getTenMon());
            holder.txtviewSellAmount1.setText(String.valueOf(food1.getSoLuongDaBan()));
            byte[] img2= food2.getImage();
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(img2,0,img2.length);
            holder.imgviewFood2.setImageBitmap(bitmap2);
            holder.txtviewPrice2.setText(String.valueOf(food2.getGia()));
            holder.txtviewFoodName2.setText(food2.getTenMon());
            holder.txtviewSellAmount2.setText(String.valueOf(food2.getSoLuongDaBan()));
        }
        else
        {

            Food food1 = foodList.get(i*2);
            byte[] img1= food1.getImage();
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(img1,0,img1.length);
            holder.imgviewFood1.setImageBitmap(bitmap1);
            holder.txtviewPrice1.setText(String.valueOf(food1.getGia()));
            holder.txtviewFoodName1.setText(food1.getTenMon());
            holder.txtviewSellAmount1.setText(String.valueOf(food1.getSoLuongDaBan()));
            holder.imgviewFood2.setImageBitmap(null);
            holder.txtviewFoodName2.setText("");
            holder.txtviewPrice2.setText("");
            holder.txtviewSellAmount2.setText("");
            holder.btnedit2.setVisibility(View.INVISIBLE);
            holder.btndel2.setVisibility(View.INVISIBLE);

        }
        database = new Database(context);
        if (i*2+1<foodList.size()) {
            Food food1 = database.getfoodbyfid(foodList.get(i * 2).getId());
            Food food2 = database.getfoodbyfid(foodList.get(i*2+1).getId());
            holder.btnedit1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.DialogSuaFood(food1.getId(), food1.getTenMon(), String.valueOf(food1.getGia()), food1.getChiTiet(), food1.getImage());
                }
            });
            holder.btndel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.DialogXoaFood(food1.getId());
                }
            });
            holder.btnedit2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.DialogSuaFood(food2.getId(), food2.getTenMon(), String.valueOf(food2.getGia()), food2.getChiTiet(), food2.getImage());
                }
            });
            holder.btndel2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.DialogXoaFood(food2.getId());
                }
            });
        }
        else
        {
            Food food1 = database.getfoodbyfid(foodList.get(i * 2).getId());
            holder.btnedit1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.DialogSuaFood(food1.getId(), food1.getTenMon(), String.valueOf(food1.getGia()), food1.getChiTiet(), food1.getImage());
                }
            });
            holder.btndel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.DialogXoaFood(food1.getId());
                }
            });
        }
        return view;
    }
}
