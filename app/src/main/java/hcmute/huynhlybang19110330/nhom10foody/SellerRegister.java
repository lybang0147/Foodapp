package hcmute.huynhlybang19110330.nhom10foody;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import hcmute.huynhlybang19110330.nhom10foody.model.AddressModel;

public class SellerRegister extends AppCompatActivity implements OnMapReadyCallback {
    EditText resname, address,info;
    Button btnregister,btnaddress;
    ImageView imgviewback,imgviewaddres;
    View fragment;
    GoogleMap mMap;
    LatLng latLng;
    Double lat,lng;
    MarkerOptions marker;
    List<Address> add;
    Database DB = new Database(this);
    ActivityResultLauncher<String> geturi = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(result);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgviewaddres.setImageBitmap(bitmap);
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }
    });
    ActivityResultLauncher<Intent> startactiforresult= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()==RESULT_OK)
            {
                String cor =  result.getData().getStringExtra("latLng");
                cor=cor.replaceAll("lat/lng: ","");
                cor=cor.replace("(","");
                cor=cor.replace(")","");
                String[] split = cor.split(",");
                lat = Double.parseDouble(split[0]);
                lng= Double.parseDouble(split[1]);
                Geocoder geocoder = new Geocoder(SellerRegister.this, Locale.getDefault());
                try {
                    add= geocoder.getFromLocation(lat,lng,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                address.setText(add.get(0).getAddressLine(0));
                latLng = new LatLng(lat,lng);
                marker = new MarkerOptions().title("").position(latLng);
                mMap.addMarker(marker);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
            }
            else {
                Status status = Autocomplete.getStatusFromIntent(result.getData());
                Toast.makeText(SellerRegister.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        }

});
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);
        AnhXa();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_seller_register);
        mapFragment.getMapAsync(this);
        imgviewaddres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    geturi.launch("image/*");
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resname.getText().toString().trim()=="" || address.getText().toString().trim()=="" || info.getText().toString().trim()=="")
                {
                    Toast.makeText(SellerRegister.this, "Please fill all the field!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imgviewaddres.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100 , byteArray);
                    byte[] hinhAnh = byteArray.toByteArray();
                    long length=hinhAnh.length/1024;
                    if (length>2000)
                    {
                        Toast.makeText(SellerRegister.this, "Image size so large!!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (DB.insertaddress(lat,lng,address.getText().toString())) {
                            AddressModel addressModel = DB.getnewaddress();

                            if (DB.insertRestaurant(resname.getText().toString().trim(), addressModel.getId(), info.getText().toString().trim(), hinhAnh, Global.getId())) {
                                Toast.makeText(SellerRegister.this, "Register successfully!!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SellerRegister.this, SellerMainPage.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SellerRegister.this, "Something Wrong!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(SellerRegister.this, "Add address failure!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        imgviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerRegister.this,Person.class);
                startActivity(intent);
            }
        });
        btnaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerRegister.this,MapsActivity.class);
                startactiforresult.launch(intent);
            }
        });
    }
    public void AnhXa()
    {
        resname=(EditText) findViewById(R.id.edittxt_sellerregister_resname);
        address=(EditText) findViewById(R.id.edittxt_sellerregister_address);
        btnregister=(Button) findViewById(R.id.btn_sellerregister_register);
        imgviewback = (ImageView) findViewById(R.id.imgview_sellerregister_back);
        info=(EditText) findViewById(R.id.edittxt_sellerregister_description);
        imgviewaddres= (ImageView) findViewById(R.id.imgview_sellerregister_addimg);
        fragment = (View) findViewById(R.id.map_seller_register);
        btnaddress = (Button) findViewById(R.id.btn_sellerregister_address);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }
}