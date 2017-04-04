package vinsoft.com.wavefindyourfriend.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import vinsoft.com.wavefindyourfriend.R;

public class MapsFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private Location mLastLocation;
    public LocationManager mLocationManager;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private GoogleApiClient mGoogleApiClient;
    Firebase roof;
    TextView tvlocation;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_maps,container,false);

        tvlocation= (TextView) view.findViewById(R.id.tv_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = ((SupportMapFragment)getContext()).getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
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
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle(getResources().getString(R.string.tiele))

                        .setMessage(getResources().getString(R.string.message))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions((Activity) getContext(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) getContext(),
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

                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mMap.setMyLocationEnabled(true);

                        LatLng sydney = new LatLng(-34, 151);
                        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


                    }

                } else {

                    Toast.makeText(getContext(), "Từ chối truy cập MyLocation", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void getCurentLocation() {
//        Log.d("Find MyLocation", "in find_location");
//        LocationManager locationManager = ((LocationManager) getContext()) .getSystemService(Context.LOCATION_SERVICE);
//        List<String> providers = locationManager.getProviders(true);
//        for (String provider : providers) {
//            locationManager.requestLocationUpdates(provider, 1000, 0,
//                    new LocationListener() {
//
//                        public void onLocationChanged(Location location) {
//                            tvlocation.setText("vi tri"+location.getLatitude()+","+location.getLongitude());
//                        }
//
//                        public void onProviderDisabled(String provider) {}
//
//                        public void onProviderEnabled(String provider) {}
//
//                        public void onStatusChanged(String provider, int status,
//                                                    Bundle extras) {}
//                    });
//            Location location = locationManager.getLastKnownLocation(provider);
//            if (location != null) {
//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
//                tvlocation.setText("vi tri"+latitude+","+longitude);
//            }
//        }
    }

}