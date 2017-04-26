package io.github.guilhermebferreira.getlocation;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapLocation extends FragmentActivity implements OnMapReadyCallback, FusedLocationListener.LocationListener {

    private GoogleMap mMap;
    EditText txtLongitude, txtLatitude;
    private Button btnDisplay;
    //private FusedLocationProviderApi fusedLocationProviderApi;
    private FusedLocationListener locationService;


    private static final int MY_PERMISSION_REQUEST_READ_FINE_LOCATION = 111;
    public static final int PRIORITY_HIGH_ACCURACY = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);


        txtLatitude = (EditText) findViewById(R.id.txtLatitude);
        txtLongitude = (EditText) findViewById(R.id.txtLongitude);
        btnDisplay = (Button) findViewById(R.id.btnLocalizar);


        addListenerOnButton();

        //getting map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationService = new FusedLocationListener(this, this);
    }






    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("I", "Map ready");


        addListenerOnMap();
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }




    @Override
    public void onReceiveLocation(Location location) {

        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Current location"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,8));


        txtLatitude.setText(String.valueOf(location.getLatitude()));
        txtLongitude.setText(String.valueOf(location.getLongitude()));


        Log.d("I", "Curent position defined");

    }

    @Override
    public void onLocationDisabled(String msg) {

    }

    @Override
    public void onConnectionFailed(String msg) {

    }

    public void addListenerOnMap(){
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng position)
            {

                mMap.addMarker(new MarkerOptions().position(position).title("Click location"));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,8));


                txtLatitude.setText(String.valueOf(position.latitude));
                txtLongitude.setText(String.valueOf(position.longitude));


                Log.d("I", "Click position defined");
            }
        });
    }

    public void addListenerOnButton() {


        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String latitude= txtLatitude.getText().toString();
                String longitude = txtLongitude.getText().toString();

                LatLng userLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Current location"));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,8));

                Log.d("I", "New position");
            }

        });

    }


}
