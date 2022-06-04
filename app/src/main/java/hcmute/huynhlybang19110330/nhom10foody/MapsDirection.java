package hcmute.huynhlybang19110330.nhom10foody;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import hcmute.huynhlybang19110330.nhom10foody.databinding.ActivityMapsDirectionBinding;

public class MapsDirection extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    private ActivityMapsDirectionBinding binding;
    MarkerOptions place1,place2;
    Polyline currentPolyline;
    Double lat1,lat2,lng1,lng2;
    ImageView imgviewback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsDirectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_direction);
        mapFragment.getMapAsync(this);
        lat1 = getIntent().getDoubleExtra("lat1",0);
        lng1 = getIntent().getDoubleExtra("lng1",0);
        lat2 = getIntent().getDoubleExtra("lat2",0);
        lng2 = getIntent().getDoubleExtra("lng2",0);
        int cartid = getIntent().getIntExtra("cartid", 0);
        imgviewback = (ImageView) findViewById(R.id.imgview_direction_back);
        place1 = new MarkerOptions().position(new LatLng(lat1,lng1)).title("Vị trí của bạn");
        place2 = new MarkerOptions().position(new LatLng(lat2,lng2)).title("Vị trí nhà hàng");
        String url = getUrl(place1.getPosition(),place2.getPosition(),"driving");
        new FetchURL(MapsDirection.this).execute(url,"");
        imgviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsDirection.this,OrderActivity.class);
                intent.putExtra("cartid",cartid);
                startActivity(intent);
            }
        });
    }

    private String getUrl(LatLng origin, LatLng dest, String directionmode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest="destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionmode;
        String parameters = str_origin +"&" +str_dest +"&" +mode;
        String output = "json";
        String url= "https://maps.googleapis.com/maps/api/directions/" + output + "?" +parameters + "&key=AIzaSyDaPPTP_IXCYlv_P25-IDMr87wWeAjwuaA";
        return url;
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
        mMap.addMarker(place1);
        mMap.addMarker(place2);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place1.getPosition(),10));
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}