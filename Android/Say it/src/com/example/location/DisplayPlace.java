package com.example.location;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.sayit.common.GetRatings;
import com.sayit.common.TaskListener;
import com.sayit.common.model.Place;

public class DisplayPlace extends Activity implements TaskListener {

	ArrayList<Place> places = new ArrayList<Place>();
	Place selectedPlace=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		places = (ArrayList<Place>) (getIntent().getExtras()
				.getSerializable("places"));
		setContentView(R.layout.activity_display_place);
		final ListView listview = (ListView) findViewById(R.id.listview);
		final PlacesListAdapter adapter = new PlacesListAdapter(this,
				android.R.layout.simple_list_item_1, places);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				selectedPlace = places.get(position);
				GetRatings ratingTask = new GetRatings(DisplayPlace.this,
						selectedPlace, DisplayPlace.this);
				ratingTask.execute();
			}
		});
		
		String dirName = "Say_it_Coupons";
        File myDirectory = new File(Environment.getExternalStorageDirectory(),
                dirName);

        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
		
		Button openSavedCoupon = (Button) findViewById(R.id.opencoupon);
        openSavedCoupon.setTextColor(Color.WHITE);
        openSavedCoupon.setTextSize(20);

        openSavedCoupon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

            	String dirName = "Say_it_Coupons";
            	File myDirectory = new File(Environment.getExternalStorageDirectory(),
        				dirName);

        		if (!myDirectory.exists()) {
        			myDirectory.mkdirs();
        		}else{
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory().toString()
                        + "/"
                        + "Say_it_Coupons/"+myDirectory.list()[0])), "image/*");
                startActivity(i);
        		}
            }
        });

	}

	@Override
	public void onTaskSuccessFul(ArrayList<Object> feedback) {
		Intent intent = new Intent(getApplicationContext(),
				FeedBackActivity.class);
		intent.putExtra("feedback", feedback);
		intent.putExtra("selectedplace", selectedPlace);
		startActivity(intent);
	}

	@Override
	public void onTaskFailure(String msg) {

	}
}
