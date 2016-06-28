package com.sayitviren.sayit.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.os.AsyncTask;

import com.sayitviren.sayit.common.model.Feedback;

public class SubmitRatings extends AsyncTask<Void, Void, ArrayList<Object>> {

	private TaskListener caller;

	public SubmitRatings(Context context, TaskListener caller) {
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
					"http://http://54.254.253.37/sayit/service/submission/");
			URLConnection urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);
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
			JSONArray array = new JSONArray(content.toString());

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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayList;
	}

}
