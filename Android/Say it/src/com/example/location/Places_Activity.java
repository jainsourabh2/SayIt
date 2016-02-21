package com.example.location;

import java.util.ArrayList;

import com.sayit.common.GetPlaces;
import com.sayit.common.TaskListener;
import com.sayit.common.model.Place;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

public class Places_Activity extends Activity implements LocationListener,
		TaskListener {

	GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places_);

		if (!isOnline()) {
			Toast.makeText(getApplicationContext(),
					"No internet connection detected", Toast.LENGTH_LONG)
					.show();
			return;
		}

		// create class object
		gps = new GPSTracker(Places_Activity.this, Places_Activity.this);

		// check if GPS enabled
		if (gps.canGetLocation()) {

			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			new GetPlaces(Places_Activity.this, "restaurant", latitude,
					longitude, this).execute();
			// \n is for new line
			/*
			 * Toast.makeText( getApplicationContext(),
			 * "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
			 * Toast.LENGTH_LONG).show();
			 */
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	public void setData(ArrayList<Place> places) {
		Intent intent = new Intent();
		intent.putExtra("places", places);

	}

	@Override
	public void onTaskSuccessFul(ArrayList<Object> places) {
		Intent intent = new Intent(getApplicationContext(), DisplayPlace.class);
		intent.putExtra("places", places);
		startActivity(intent);

	}

	@Override
	public void onTaskFailure(String msg) {

	}
}
