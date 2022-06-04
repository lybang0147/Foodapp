package hcmute.huynhlybang19110330.nhom10foody;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import hcmute.huynhlybang19110330.nhom10foody.adapter.SellerFoodAdapter;
import hcmute.huynhlybang19110330.nhom10foody.model.Food;
import hcmute.huynhlybang19110330.nhom10foody.model.Restaurant;

public class SellerMainPage extends AppCompatActivity {
    EditText search;
    ListView lvfood;
    Button btnSearch, btnHome, btnOrder, btnNotice, btnPerson;
    ArrayList<Food> arrfood;
    SellerFoodAdapter adapter;
    TextView txtviewaddfood;
    Database database;
    ImageView imageedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main_page);
        Anhxa();
        database = new Database(this);
        Database database = new Database(this);
        arrfood = new ArrayList<>();
        adapter = new SellerFoodAdapter(this, R.layout.sellermainpage_listview_line, arrfood);
        lvfood.setAdapter(adapter);
        getdatafood();
        txtviewaddfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerMainPage.this, AddFood.class);
                startActivity(intent);
            }
        });
        btnPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerMainPage.this,Person.class);
                intent.putExtra("status",2);
                startActivity(intent);
            }
        });
    }

    public void getdatafood() {
        int flag = 1;
        Cursor datafood = database.getrestaurantfood(Global.getId());
        arrfood.clear();
        while (datafood.moveToNext()) {
            arrfood.add(new Food(
                    datafood.getInt(0),
                    datafood.getString(1),
                    datafood.getInt(2),
                    datafood.getBlob(3),
                    datafood.getInt(4),
                    (Boolean) (datafood.getInt(5) == 1)
            ));

        }
        adapter.notifyDataSetChanged();
    }

    public void Anhxa() {
        search = (EditText) findViewById(R.id.edittext_nguoibantrangchinh_search);
        lvfood = (ListView) findViewById(R.id.listview_nguoibantrangchinh_sanpham);
        btnSearch = (Button) findViewById(R.id.button_nguoibantrangchinh_search);
        btnHome = (Button) findViewById(R.id.button_nguoibantrangchinh_home);
        btnOrder = (Button) findViewById(R.id.button_nguoibantrangchinh_donhang);
        btnNotice = (Button) findViewById(R.id.button_nguoibantrangchinh_thongbao);
        btnPerson = (Button) findViewById(R.id.button_nguoibantrangchinh_canhan);
        txtviewaddfood = (TextView) findViewById(R.id.txtview_nguoibantrangchinh_themmonan);
    }
   ActivityResultLauncher<String> geturi = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(result);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageedit.setImageBitmap(bitmap);
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }
    });
    public void DialogSuaFood(int id, String name, String price, String detail, byte[] image)
    {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_edit_food);
        imageedit = (ImageView) dialog.findViewById(R.id.imgview_editfood_editimg);
        EditText edittxtname= (EditText) dialog.findViewById(R.id.edittxt_editfood_name);
        EditText edittxtprice= (EditText) dialog.findViewById(R.id.edittxt_editfood_price);
        EditText edittxtdetail = (EditText) dialog.findViewById(R.id.edittxt_editfood_detail);
        Button btnedit = (Button) dialog.findViewById(R.id.btn_editfood_editbtn);
        Button btncancel = (Button) dialog.findViewById(R.id.btn_editfood_cancel);
        edittxtname.setText(name);
        edittxtprice.setText(price);
        edittxtdetail.setText(detail);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        imageedit.setImageBitmap(bitmap);
        imageedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    geturi.launch("image/*");
                }
        });

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edittxtname.getText().toString().trim()=="" || edittxtprice.getText().toString().trim()=="" || edittxtdetail.getText().toString().trim()=="")
                {
                    Toast.makeText(SellerMainPage.this, "Please fill all the field", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imageedit.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100 , byteArray);
                    byte[] hinhAnh = byteArray.toByteArray();
                    if (database.updateFood(id,edittxtname.getText().toString().trim(),edittxtprice.getText().toString().trim(),edittxtdetail.getText().toString().trim(),hinhAnh))
                    {
                        Toast.makeText(SellerMainPage.this, "Update Success!!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        getdatafood();
                    }
                }
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                getdatafood();
            }
        });
        dialog.show();
    }
    public void DialogXoaFood(int id)
    {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có chắc chắn muốn xóa món ăn này không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (database.deleteFood(id))
                {
                    Toast.makeText(SellerMainPage.this, "Đã xóa thành công", Toast.LENGTH_SHORT).show();
                    getdatafood();
                }
                else
                    Toast.makeText(SellerMainPage.this, "Something Wrong!!!", Toast.LENGTH_SHORT).show();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialogXoa.show();
    }
}