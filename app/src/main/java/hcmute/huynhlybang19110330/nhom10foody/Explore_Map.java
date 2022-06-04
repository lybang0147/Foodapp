package hcmute.huynhlybang19110330.nhom10foody;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hcmute.huynhlybang19110330.nhom10foody.databinding.ActivityExploreMapBinding;
import hcmute.huynhlybang19110330.nhom10foody.model.Restaurant;

public class Explore_Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityExploreMapBinding binding;
    EditText edittxtsearch;
    ImageView imgviewback;
    ArrayList<Restaurant> arrres;
    ArrayList<MarkerOptions> arrmarker;
    Database database;
    LatLng latLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExploreMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_explore);
        mapFragment.getMapAsync(this);
        arrres = new ArrayList<>();
        arrmarker = new ArrayList<>();
        database = new Database(this);
        edittxtsearch = (EditText) findViewById(R.id.edittxt_exploremap_search);
        imgviewback = (ImageView) findViewById(R.id.imgview_exploremap_back);

        ActivityResultLauncher<Intent> startforresult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode() == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(result.getData());
                    edittxtsearch.setText(place.getAddress());
                    latLng = place.getLatLng();
                    MarkerOptions marker;
                    marker = new MarkerOptions().title("").position(latLng);
                    mMap.addMarker(marker);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,30));
                }
                else if (result.getResultCode()== AutocompleteActivity.RESULT_ERROR)
                {
                    Status status = Autocomplete.getStatusFromIntent(result.getData());
                    Toast.makeText(Explore_Map.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Places.initialize(getApplicationContext(),"AIzaSyBLp7mqh_CuiM6lEO3Y_k7eO5cghw0q8mo");
        edittxtsearch.setFocusable(false);
        edittxtsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                        ,Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(Explore_Map.this);
                startforresult.launch(intent);
            }
        });
        getdatares();
        imgviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Explore_Map.this,MainPage.class);
                startActivity(intent);
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void getdatares()
    {

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Context mContext = getApplicationContext();
                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            } });
        Cursor cursor = database.getallrestaurant();
        int resid = getIntent().getIntExtra("resid",0);
        int markerid=0;
        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                Restaurant res =new Restaurant(
                        cursor.getInt(0),
                        cursor.getString(1),
                        database.getaddressbyid(cursor.getInt(2)),
                        cursor.getString(3),
                        cursor.getBlob(4),
                        cursor.getInt(5),
                        cursor.getFloat(6)
                );
                if (resid==res.getId())
                    markerid=cursor.getPosition();
                MarkerOptions marker = new MarkerOptions().title(res.getTenNhaHang()).position(new LatLng(res.getAddress().getLat(),res.getAddress().getLng())).snippet(res.getMota() + "\n" + res.getAddress().getFulladdress());
                arrmarker.add(marker);
                mMap.addMarker(marker);
            }
            if (resid!=0) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arrmarker.get(markerid).getPosition(), 30));
            }
            else
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arrmarker.get(resid).getPosition(),15));
        }
        // Add a marker in Sydney and move the camera
    }
}