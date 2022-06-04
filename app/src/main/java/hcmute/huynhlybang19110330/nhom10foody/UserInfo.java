package hcmute.huynhlybang19110330.nhom10foody;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.widget.Autocomplete;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import hcmute.huynhlybang19110330.nhom10foody.model.UserInfoModel;

public class UserInfo extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    LatLng latLng;
    Double lat,lng;
    List<Address> add;
    MarkerOptions marker;
    ImageView imgviewuser,imgviewback;
    TextView txtviewname, txtviewaddress;
    Button btnupdateinfo;
    Database database = new Database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        AnhXa();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_userinfo_register);
        mapFragment.getMapAsync(this);
        UserInfoModel userInfo = database.getuserinfo(Global.getId());
        txtviewname.setText(userInfo.getName());
        txtviewaddress.setText(userInfo.getAddress().getFulladdress());
        lat = userInfo.getAddress().getLat();
        lng = userInfo.getAddress().getLng();
        latLng = new LatLng(lat,lng);
        byte[] img1= userInfo.getImage();
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(img1,0,img1.length);
        imgviewuser.setImageBitmap(bitmap1);
        btnupdateinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfo.this,UpdateUserInfo.class);
                intent.putExtra("infoid",userInfo.getId());
                startActivity(intent);
            }
        });
        imgviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInfo.this,MainPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;
        marker = new MarkerOptions().title("").position(latLng);
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }
    public void AnhXa()
    {
        imgviewuser = (ImageView)  findViewById(R.id.imgview_userinfo_avatar);
        txtviewname = (TextView) findViewById(R.id.textview_userinfo_username);
        txtviewaddress = (TextView) findViewById(R.id.textview_userinfo_address);
        btnupdateinfo = (Button) findViewById(R.id.button_userinfo_gotoupdate);
        imgviewback = (ImageView) findViewById(R.id.imgview_userinfo_back);
    }
}