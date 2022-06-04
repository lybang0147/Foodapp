package hcmute.huynhlybang19110330.nhom10foody;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

import hcmute.huynhlybang19110330.nhom10foody.model.Restaurant;

public class AddFood extends AppCompatActivity {
    Bitmap bitmap;
    byte[] bitimg;
    Database DB;
    private ImageView imageupload;
    EditText foodName, foodPrice,foodDetail;
    Button btnAdd,btnCancel;
    ActivityResultLauncher<String> geturi = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(result);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageupload.setImageBitmap(bitmap);
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        final Database database = new Database(this);
        AnhXa();
        imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geturi.launch("image/*");
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foodName.getText().toString().trim()=="" || foodPrice.getText().toString().trim()=="" || foodDetail.getText().toString().trim()=="")
                {
                    Toast.makeText(AddFood.this, "Please fill all the field!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imageupload.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    Restaurant restaurant = database.getrestaurantbyuid(Global.getId());
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100 , byteArray);
                    byte[] hinhAnh = byteArray.toByteArray();
                    long length=hinhAnh.length/1024;
                    if (length>2000)
                    {
                        Toast.makeText(AddFood.this, "Image size so large", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (database.insertfood(foodName.getText().toString().trim(), Integer.parseInt(foodPrice.getText().toString().trim()), foodDetail.getText().toString().trim(), hinhAnh, restaurant.getId())) {
                            Intent intent = new Intent(AddFood.this, SellerMainPage.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AddFood.this, "Something Wrong!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddFood.this,MainPage.class));
            }
        });

    }
    public void AnhXa()
    {
        imageupload = (ImageView) findViewById(R.id.imgview_addfood_addimg);
        foodName = (EditText) findViewById(R.id.edittxt_addfood_name);
        foodPrice= (EditText) findViewById(R.id.edittxt_addfood_price);
        foodDetail = (EditText) findViewById(R.id.edittxt_addfood_detail);
        btnAdd = (Button) findViewById(R.id.btn_addfood_add);
        btnCancel = (Button) findViewById(R.id.btn_addfood_cancel);
    }
}