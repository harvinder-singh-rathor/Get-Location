package rathor.harvinder.getlocation;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MyMapActivity extends AppCompatActivity implements OnMapReadyCallback,LocationCallback {
    private static final int REQUEST_LOCATION_PERMISSION = 4323;
    private GoogleMap googleMap;
    private LocationUpdater updater;
    private LocationManager lm;
    private static final int zoomLevel=17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);
        initMap();

    }

    private void initMap() {
        updater = new LocationUpdater(this, this);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (googleMap == null) {
            // googleMap = getMapFragment().getMap();
            SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            fragment.getMapAsync(this);
        }
    }

    void addSomeMarker(Location location){
        if(googleMap!=null&&location!=null){
            googleMap.clear();
            // Add a marker in Sydney, Australia, and move the camera.
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            this.googleMap.addMarker(new MarkerOptions().position(latLng).title("Here I am"));
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(zoomLevel).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (Permissions.checkLocationServicesPermission(MyMapActivity.this)) {
            updater.checkGooglePlayServices();

            this.googleMap.setMyLocationEnabled(true);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    if(googleMap!=null){
                        this.googleMap.setMyLocationEnabled(true);
                    }
                }
                else{
                    Toast.makeText(this, "You have denied the permission!!!!!!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        addSomeMarker(location);
    }

    @Override
    public void isGoogleServiceAvailable(boolean isAvailable, String message) {
        if (isAvailable) {
            updater.connect();
        }
    }

    @Override
    public void isConnected(boolean isConnected) {
        if (isConnected) {
            updater.requestUpdate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(updater!=null&&updater.isGoogleApiClientConnected()){
            updater.disconnect();
        }
    }
}
