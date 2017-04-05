package vinsoft.com.wavefindyourfriend.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vinsoft.com.wavefindyourfriend.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    Firebase roof;
    TextView tvlocation;
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        tvlocation= (TextView) findViewById(R.id.tv_location);
        Firebase.setAndroidContext(this);
        roof=new Firebase("https://chatandmap.firebaseio.com");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //MyLocation Permission already granted
                // buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                getCurentLocation();

            } else {
                //Request MyLocation Permission
                checkLocationPermission();
                getCurentLocation();
            }
        } else {
            mMap.setMyLocationEnabled(true);

        }


    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.tiele))

                        .setMessage(getResources().getString(R.string.message))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == grantResults[0]) {
//            mMap.setMyLocationEnabled(true);
//            LatLng sydney = new LatLng(-34, 151);
//            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        }

        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mMap.setMyLocationEnabled(true);

                        LatLng sydney = new LatLng(-34, 151);
                        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


                    }

                } else {

                    Toast.makeText(this, "Từ chối truy cập MyLocation", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void getCurentLocation() {
        Log.d("Find MyLocation", "in find_location");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {

            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                tvlocation.setText("vi tri"+latitude+","+longitude);
            }

            locationManager.requestLocationUpdates(provider, 1000, 0,
                    new LocationListener() {

                        public void onLocationChanged(Location location) {
                             latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            tvlocation.setText("vi tri"+latitude+","+longitude);
                        }

                        public void onProviderDisabled(String provider) {}

                        public void onProviderEnabled(String provider) {}

                        public void onStatusChanged(String provider, int status,
                                                    Bundle extras) {}
                    });

        }

        Map<String, Object> childUpdates = new HashMap<String, Object>() ;
        childUpdates.put("latitude",latitude);
        childUpdates.put("longitude",longitude);
        roof.child("database").child("Location").child(SignInActivity.person.getId()).updateChildren(childUpdates);
    }


}
