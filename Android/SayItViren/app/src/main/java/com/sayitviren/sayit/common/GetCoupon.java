package com.sayitviren.sayit.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

public class GetCoupon extends AsyncTask<Void, Void, ArrayList<Object>> {

	private TaskListener caller;
	private JSONArray jsonParam;

	public GetCoupon(Context context, TaskListener caller, JSONArray jsonParam) {
		this.caller = caller;
		this.jsonParam = jsonParam;
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
		StringBuilder sb = new StringBuilder();

		String http = "http://52.77.209.239/sayit/service/submission/";

		HttpURLConnection urlConnection = null;
		try {

			URL url = new URL(http);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");
			urlConnection.setUseCaches(false);
			String charset = "UTF-8";
			String query ="";
		    try {
				query = String.format("category=%s&"
				                        + "feedback=%s&" 
				                        + "devicemetrics=%s&",
				                        URLEncoder.encode(jsonParam.get(0).toString(), charset),
				                        URLEncoder.encode(jsonParam.get(1).toString(), charset),
				                        URLEncoder.encode(jsonParam.get(2).toString(), charset));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

			
			
			urlConnection.connect();

			OutputStreamWriter out = new OutputStreamWriter(
					urlConnection.getOutputStream());
			out.write(query);
			out.close();

			int HttpResult = urlConnection.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
			} 
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
		ArrayList<Object> obj = new ArrayList<Object>();
		obj.add(sb.toString());
		return obj;
	}

}
