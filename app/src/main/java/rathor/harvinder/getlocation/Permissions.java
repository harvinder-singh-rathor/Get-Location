package rathor.harvinder.getlocation;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by Harvinder on 27-06-2018.
 */

public class Permissions {
    public static boolean checkLocationServicesPermission(Context con) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (((Activity) con)
                    .checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {

                return true;
            }
        } else {
            return true;
        }


    }
}
