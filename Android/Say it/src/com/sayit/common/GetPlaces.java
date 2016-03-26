package com.sayit.common;

import java.util.ArrayList;


import android.content.Context;
import android.os.AsyncTask;

public class GetPlaces extends AsyncTask<Void, Void, ArrayList<Object>> {

	private String places;
	private double latitude;
	private double longitude;
	private TaskListener caller;

	public GetPlaces(Context context, String places,double latitude,double longitude ,TaskListener caller) {
		this.places = places;
		this.latitude = latitude;
		this.longitude = longitude;
		this.caller = caller;
		
	}

	@Override
	protected void onPostExecute(ArrayList<Object> result) {
		super.onPostExecute(result);
		caller.onTaskSuccessFul(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected ArrayList<Object> doInBackground(Void... arg0) {
		PlacesWebServiceCallManager service = new PlacesWebServiceCallManager(
				"AIzaSyA_np8KqwOY-fAw-RJksI1RSsD_hzG6PpA");
		ArrayList<Object> findPlaces = service.findPlaces(latitude, // 28.632808
				longitude, places); // 77.218276

		return findPlaces;
	}
}