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
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import hcmute.huynhlybang19110330.nhom10foody.model.AddressModel;

public class UpdateUserInfo extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    ImageView imgviewuser,imgviewback;
    EditText edittxtviewname, edittxtviewaddress;
    Button btnaddress,btnupdateinfo;
    LatLng latLng;
    int type;
    Double lat,lng;
    MarkerOptions marker;
    List<Address> add;
    Database database = new Database(this);
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
                Geocoder geocoder = new Geocoder(UpdateUserInfo.this, Locale.getDefault());
                try {
                    add= geocoder.getFromLocation(lat,lng,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                edittxtviewaddress.setText(add.get(0).getAddressLine(0));
                latLng = new LatLng(lat,lng);
                marker = new MarkerOptions().title("").position(latLng);
                mMap.addMarker(marker);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
            }
            else {
                Status status = Autocomplete.getStatusFromIntent(result.getData());
                Toast.makeText(UpdateUserInfo.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    });
    ActivityResultLauncher<String> geturi = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(result);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgviewuser.setImageBitmap(bitmap);
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        AnhXa();
        type = getIntent().getIntExtra("infoid",0);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_updateuserinfo);
        mapFragment.getMapAsync(this);
        imgviewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geturi.launch("image/*");
            }
        });
        btnaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateUserInfo.this,MapsActivity.class);
                startactiforresult.launch(intent);
            }
        });
        btnupdateinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edittxtviewname.getText().toString().trim().equals("") || edittxtviewaddress.getText().toString().trim().equals(""))
                {
                    Toast.makeText(UpdateUserInfo.this, "Please fill all the field!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) imgviewuser.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100 , byteArray);
                    byte[] hinhAnh = byteArray.toByteArray();
                    long length=hinhAnh.length/1024;
                    if (length>2000)
                    {
                        Toast.makeText(UpdateUserInfo.this, "Image size so large!!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (database.insertaddress(lat,lng,edittxtviewaddress.getText().toString()))
                        {
                            AddressModel addressModel = database.getnewaddress();
                            if (type!=0)
                            {
                                if (database.updateuserinfo(type,Global.getId(),edittxtviewname.getText().toString(),addressModel.getId(),hinhAnh))
                                {
                                    Intent intent = new Intent(UpdateUserInfo.this,UserInfo.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(UpdateUserInfo.this, "Something Wrong UP!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                if (database.insertuserinfo(Global.getId(), edittxtviewname.getText().toString(), addressModel.getId(), hinhAnh)) {
                                    Intent intent = new Intent(UpdateUserInfo.this, UserInfo.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(UpdateUserInfo.this, "Something Wrong!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(UpdateUserInfo.this, "Add address fail!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }
    public void AnhXa()
    {
        imgviewuser = (ImageView) findViewById(R.id.imgview_updateuserinfo_avatar);
        imgviewback = (ImageView) findViewById(R.id.imgview_updateuserinfo_back);
        edittxtviewname = (EditText) findViewById(R.id.edittxt_updateuserinfo_username);
        edittxtviewaddress = (EditText) findViewById(R.id.edittxt_updateuserinfo_address);
        btnaddress = (Button) findViewById(R.id.btn_updateuserinfo_address);
        btnupdateinfo = (Button) findViewById(R.id.button_updateuserinfo_update);
    }
}