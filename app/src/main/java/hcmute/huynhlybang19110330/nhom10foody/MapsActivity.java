package hcmute.huynhlybang19110330.nhom10foody;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.VoicemailContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLEngineResult;

import hcmute.huynhlybang19110330.nhom10foody.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    MarkerOptions marker;
    LatLng location;
    EditText edittxtsearch;
    LatLng latLng;
    ArrayList<LatLng> arrlaln = new ArrayList<LatLng>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AnhXa();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        location = new LatLng(10.82,106.63);
        marker = new MarkerOptions().title("Ho Chi Minh City, Viet Nam")
                .position(new LatLng(10.82,106.63));
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
                    final Intent data = new Intent();
                    data.putExtra("latLng",latLng.toString());
                    setResult(Activity.RESULT_OK,data);
                    finish();
                }
                else if (result.getResultCode()== AutocompleteActivity.RESULT_ERROR)
                {
                    Status status = Autocomplete.getStatusFromIntent(result.getData());
                    Toast.makeText(MapsActivity.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Places.initialize(getApplicationContext(),"AIzaSyDaPPTP_IXCYlv_P25-IDMr87wWeAjwuaA");
        edittxtsearch.setFocusable(false);
        edittxtsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                        ,Place.Field.LAT_LNG,Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(MapsActivity.this);
                startforresult.launch(intent);
            }
        });

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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,20));

    }
    public void AnhXa()
    {
        edittxtsearch = (EditText) findViewById(R.id.edittxt_map_search);
    }
}