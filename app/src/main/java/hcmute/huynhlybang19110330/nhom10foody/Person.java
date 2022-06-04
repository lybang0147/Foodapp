package hcmute.huynhlybang19110330.nhom10foody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import hcmute.huynhlybang19110330.nhom10foody.model.UserInfoModel;

public class Person extends AppCompatActivity {
    ImageView imgviewBack;
    Button btnPersonName,btnPayment,btnYourRestaurant,btnlogout;
    Database DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        DB = new Database(this);
        Anhxa();
        if (getIntent().getIntExtra("status",0)==2)
        {
            btnYourRestaurant.setText("TRANG NGƯỜI MUA");
            btnYourRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Person.this,MainPage.class);
                    startActivity(intent);
                }
            });
            imgviewBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Person.this,SellerMainPage.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            btnYourRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Global.getId()==-1)
                    {
                        Toast.makeText(Person.this, "Please login to become a seller!!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Person.this,Login.class);
                        startActivity(intent);
                    }
                    else {


                        if (DB.checkrestaurant(Global.getId()))
                        {
                            Intent intent = new Intent(Person.this,SellerMainPage.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(Person.this, "Please fill the form!!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Person.this,SellerRegister.class);
                            startActivity(intent);
                        }
                    }
                }
            });
            imgviewBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Person.this,MainPage.class);
                    startActivity(intent);
                }
            });
        }
        if (Global.getId()==-1)
        {
            btnPersonName.setText(Global.getName().toString());
            btnlogout.setText("ĐĂNG NHẬP");
        }
        else
        {
            btnPersonName.setText(Global.getName().toString());
            btnlogout.setText("ĐĂNG XUẤT");
        }


        imgviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Person.this,MainPage.class);
                startActivity(intent);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.getId()==-1) {
                    Intent intent = new Intent(Person.this,Login.class);
                    startActivity(intent);
                }
                else {
                    Global.setId(-1);
                    Global.setName("Guest");
                    Global.setPhone("");
                    Intent intent = new Intent(Person.this, MainPage.class);
                    startActivity(intent);
                }
            }
        });
        btnPersonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.getId()==-1)
                {
                    Toast.makeText(Person.this, "Please login to continue!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Person.this,Login.class);
                    startActivity(intent);
                }
                else
                {
                    UserInfoModel user = DB.getuserinfo(Global.getId());
                    if (user!=null)
                    {
                        Intent intent = new Intent(Person.this,UserInfo.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(Person.this,UpdateUserInfo.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }
    public void Anhxa()
    {
        imgviewBack = (ImageView) findViewById(R.id.imgview_person_back);
        btnPersonName = (Button) findViewById(R.id.btn_person_personname);
        btnPayment = (Button) findViewById(R.id.btn_person_payment);
        btnYourRestaurant = (Button) findViewById(R.id.btn_person_restaurant);
        btnlogout = (Button) findViewById(R.id.btn_person_logout);

    }

}