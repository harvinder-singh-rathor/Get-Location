package rathor.harvinder.getlocation;

import android.location.Location;

public interface LocationCallback {
	public void onLocationChanged(Location location);

	public void isGoogleServiceAvailable(boolean isAvailable, String message);

	public void isConnected(boolean isConnected);

}
