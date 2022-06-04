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

import java.util.List;

import hcmute.huynhlybang19110330.nhom10foody.Database;
import hcmute.huynhlybang19110330.nhom10foody.MainPage;
import hcmute.huynhlybang19110330.nhom10foody.MonAnActivity;
import hcmute.huynhlybang19110330.nhom10foody.R;
import hcmute.huynhlybang19110330.nhom10foody.model.Restaurant;

public class RestaurantAdapter extends BaseAdapter {
    private MainPage context;
    private int layout;
    private List<Restaurant> resList;
    Database database;
    public RestaurantAdapter(MainPage context, int layout, List<Restaurant> resList) {
        this.context = context;
        this.layout = layout;
        this.resList=resList;
    }
    private class ViewHolder{
        ImageView imgviewRes1, imgviewRes2;
        TextView txtviewresdescription1, txtviewResName1, txtviewCmt1,txtviewresdescription2, txtviewResName2, txtviewCmt2;
    }
    @Override
    public int getCount() {
        if (resList.size()%2==0)
        {
            return resList.size()/2;
        }
        return resList.size()/2+1;
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
            holder.imgviewRes1 = (ImageView) view.findViewById(R.id.imgview_mainpageline_firstimg);
            holder.imgviewRes2 = (ImageView) view.findViewById(R.id.imgview_mainpageline_secondimg);
            holder.txtviewResName1 = (TextView) view.findViewById(R.id.txtview_mainpageline_firstfoodname);
            holder.txtviewResName2 = (TextView) view.findViewById(R.id.txtview_mainpageline_secondfoodname);
            holder.txtviewCmt1 = (TextView) view.findViewById(R.id.txtview_mainpageline_firstresname);
            holder.txtviewCmt2 = (TextView) view.findViewById(R.id.txtview_mainpageline_secondresname);
            holder.txtviewresdescription1 = (TextView) view.findViewById(R.id.txtview_mainpageline_firstprice);
            holder.txtviewresdescription2 = (TextView) view.findViewById(R.id.txtview_mainpageline_secondprice);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        if (i*2+1<resList.size())
        {
            Restaurant res1 = resList.get(i*2);
            Restaurant res2 = resList.get(i*2+1);
            byte[] img1 = res1.getImage();
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(img1,0,img1.length);
            holder.imgviewRes1.setImageBitmap(bitmap1);
            holder.txtviewResName1.setText(res1.getTenNhaHang());
            holder.txtviewresdescription1.setVisibility(View.INVISIBLE);
            holder.txtviewCmt1.setVisibility(View.INVISIBLE);
            byte[] img2 = res2.getImage();
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(img2,0,img2.length);
            holder.imgviewRes2.setImageBitmap(bitmap2);
            holder.txtviewResName2.setText(res2.getTenNhaHang());
            holder.txtviewresdescription2.setVisibility(View.INVISIBLE);
            holder.txtviewCmt2.setVisibility(View.INVISIBLE);
            holder.imgviewRes1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, MonAnActivity.class);
                intent.putExtra("restaurantid",res1.getId());
                context.startActivity(intent);
                }
            });
            holder.imgviewRes2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, MonAnActivity.class);
                    intent.putExtra("restaurantid",res2.getId());
                    context.startActivity(intent);
                }
            });
        }
        else
        {
            Restaurant res1 = resList.get(i*2);
            byte[] img1 = res1.getImage();
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(img1,0,img1.length);
            holder.imgviewRes1.setImageBitmap(bitmap1);
            holder.txtviewResName1.setText(res1.getTenNhaHang());
            holder.txtviewresdescription1.setVisibility(View.INVISIBLE);
            holder.txtviewCmt1.setVisibility(View.INVISIBLE);
            holder.imgviewRes2.setImageBitmap(null);
            holder.txtviewResName2.setText("");
            holder.txtviewresdescription2.setVisibility(View.INVISIBLE);
            holder.txtviewCmt2.setVisibility(View.INVISIBLE);
            holder.imgviewRes1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, MonAnActivity.class);
                    intent.putExtra("restaurantid",res1.getId());
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }
}
