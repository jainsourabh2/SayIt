package com.sayitviren.sayit.common;

import java.util.ArrayList;

import com.sayitviren.sayit.common.model.Place;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetPlaces extends AsyncTask<Void, Void, ArrayList<Object>> {

	private String places;
	private double latitude;
	private double longitude;
	private TaskListener caller;
	private Context context;
	ProgressDialog progress;
	public GetPlaces(Context context, String places,double latitude,double longitude ,TaskListener caller) {
		this.places = places;
		this.latitude = latitude;
		this.longitude = longitude;
		this.caller = caller;
		this.context = context;
		
	}

	@Override
	protected void onPostExecute(ArrayList<Object> result) {
		super.onPostExecute(result);
		progress.dismiss();
		caller.onTaskSuccessFul(result);
	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(this.context, "",
				"Searching", true);
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