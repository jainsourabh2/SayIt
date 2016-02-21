package com.example.location;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.sayit.common.GetCoupon;
import com.sayit.common.TaskListener;
import com.sayit.common.model.Feedback;
import com.sayit.common.model.Place;

public class FeedBackActivity extends Activity implements TaskListener {

	ArrayList<Feedback> feedback = new ArrayList<Feedback>();
	Place place;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.display_feedback);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.feedback_header);
		feedback = (ArrayList<Feedback>) (getIntent().getExtras()
				.getSerializable("feedback"));
		final ListView listview = (ListView) findViewById(R.id.listview);
		final FeedBackListAdapter adapter = new FeedBackListAdapter(this,
				android.R.layout.simple_list_item_1, feedback);
		listview.setAdapter(adapter);
		listview.setDivider(null);
		listview.setDividerHeight(0);
		place = (Place) (getIntent().getExtras()
				.getSerializable("selectedplace"));
		TextView selectPlace = (TextView) findViewById(R.id.selectedplaceName);
		TextView selectPlaceLocation = (TextView) findViewById(R.id.selectedplaceLocation);
		ImageView iconView = (ImageView) findViewById(R.id.selectedplaceIcon);

		selectPlace.setText(place.getName());
		selectPlaceLocation.setText(place.getVicinity());
		AQuery aq = new AQuery(this);

		aq.id(iconView).image(place.getIcon(), true, true, 0, 0, null,
				AQuery.FADE_IN_NETWORK, 1.0f);

		Button back = (Button) findViewById(R.id.backButtonFeedback);

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		Button submitFeedback = (Button) findViewById(R.id.button1);
		submitFeedback.setTextSize(20);
		submitFeedback.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SeekBar sb = null;
				JSONArray feedbackArray = new JSONArray();

				for (int i = 0; i < listview.getCount(); i++) {
					JSONObject jonObj = new JSONObject();
					sb = (SeekBar) listview.getChildAt(i).findViewById(
							R.id.seekBar1);
					Feedback feedback = (Feedback) listview.getAdapter()
							.getItem(i);
					try {
						jonObj.put("f", feedback.getAttributeIndex());
						jonObj.put("v", sb.getProgress());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					feedbackArray.put(jonObj);
				}

				JSONObject deviceObj = getDeviceInfo();
				JSONArray finalObj = new JSONArray();
				finalObj.put(place.getType());
				finalObj.put(feedbackArray);
				finalObj.put(deviceObj);
				GetCoupon gt = new GetCoupon(FeedBackActivity.this,
						FeedBackActivity.this, finalObj);
				gt.execute();
				/*
				 * ArrayList<Object> coupon = new ArrayList<Object>();
				 * coupon.add(
				 * "<!DOCTYPE html><html lang=\"en-us\"><head><style>.coupan {float: left; margin: 5px; padding: 15px; width: 250px; height: 300px; border: 1px solid black; background-color: #ff8000;}.text-shadow { text-shadow: 1px 1px 1px #444; letter-spacing: 2px;}.text-white { color: #fff !important;}.text-white1 { color: #fff !important; font-size: 20px; margin: -5px 1px;}.text1 { color: #000fff; font-size: 14px; text-align: center; margin: -5px 1px;}.code { float: right; margin: -5px 40px; letter-spacing: 1px; padding: 10px; width: 100px; height: 20px; border: 1px solid black; background-color: #ffffff;}</style></head><body> <div class=\"coupan\"> <h2>eBay</h2> <p class=\"text-white text-shadow\"> 	Shop & Get Free Prepaid Mobile Recharge Worth Rs. 200</p> <br /> <div> 	<span class=\"text-white1\"> Code: </span> 	<p class=\"code\">EBY200</p> </div> <br /> <div> 	<p> 		<span class=\"text1\">Expires on 21 Dec</span> 	</p> </div> </div></body></html>"
				 * ); onTaskSuccessFul(coupon);
				 */
			}
		});
	}

	@Override
	public void onTaskSuccessFul(ArrayList<Object> coupon) {
		Intent intent = new Intent(getApplicationContext(), DisplayCoupon.class);
		intent.putExtra("coupon", coupon);
		startActivity(intent);
	}

	@Override
	public void onTaskFailure(String msg) {
	}

	private JSONObject getDeviceInfo() {
		JSONObject deviceObj = new JSONObject();

		try {
			deviceObj.put("pf", "Android");
			deviceObj.put("osv", System.getProperty("os.version"));
			deviceObj.put("did", Secure.getString(this.getContentResolver(),
					Secure.ANDROID_ID));
			deviceObj.put("mnu", System.getProperty("os.Build.MANUFACTURER"));
			deviceObj.put("mod", System.getProperty("os.Build.MODEL"));
			deviceObj.put("rtc", System.currentTimeMillis());
			deviceObj
					.put("email",
							AccountManager.get(this).getAccountsByType(
									"com.google")[0].name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deviceObj;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent myIntent = new Intent(getApplicationContext(),
				DisplayPlace.class);
		startActivityForResult(myIntent, 0);
		return true;

	}
}
