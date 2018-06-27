package rathor.harvinder.getlocation;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MyMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_LOCATION_PERMISSION = 4323;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);
        initMap();
    }

    private void initMap() {
        if (googleMap == null) {
            // googleMap = getMapFragment().getMap();
            SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            fragment.getMapAsync(this);
        }
    }

    void addSomeMarker(){
        if(googleMap!=null){
            // Add a marker in Sydney, Australia, and move the camera.
            LatLng sydney = new LatLng(-34, 151);
            this.googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        addSomeMarker();
        if (Permissions.checkLocationServicesPermission(MyMapActivity.this)) {
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
}
