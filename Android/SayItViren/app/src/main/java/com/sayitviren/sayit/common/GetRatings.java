package com.sayitviren.sayit.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sayitviren.sayit.common.model.Feedback;
import com.sayitviren.sayit.common.model.Place;

import android.content.Context;
import android.os.AsyncTask;

public class GetRatings extends AsyncTask<Void, Void, ArrayList<Object>> {

	private Place place;
	private TaskListener caller;

	public GetRatings(Context context, Place place, TaskListener caller) {
		this.place = place;
		//this.latitude = latitude;
		//this.longitude = longitude;
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
		StringBuilder content = new StringBuilder();
		try {
			URL url = new URL(
					"http://52.77.209.239/sayit/service/cat/"+place.getType());
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()), 8);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line + "\n");
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<Object> arrayList = null;
		try { 
			String text = content.toString().substring(1, content.length()-2).replace("\\\"", "\"");
			JSONArray array = new JSONArray(text);
			//JSONArray array = new JSONArray("[{\"f\":\"1\",\"v\":\"Discount\"},{\"f\":\"2\",\"v\":\"Ambience\"},{\"f\":\"3\",\"v\":\"Price\"},{\"f\":\"4\",\"v\":\"Food Taste\"},{\"f\":\"5\",\"v\":\"Hygiene\"}]"); 

			arrayList = new ArrayList<Object>();
			for (int i = 0; i < array.length(); i++) {
				try {
					Feedback feedback = Feedback
							.jsonToPontoReferencia((JSONObject) array.get(i));
					// Log.v("Places Services ", "" + place);
					arrayList.add(feedback);
				} catch (Exception e) {
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return arrayList;
	}

}
