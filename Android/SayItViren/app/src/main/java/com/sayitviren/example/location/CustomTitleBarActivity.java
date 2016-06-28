package com.sayitviren.example.location;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.sayitviren.R;
import com.sayitviren.sayit.common.model.Feedback;


public class CustomTitleBarActivity extends Activity {


	ArrayList<Feedback> feedback = new ArrayList<Feedback>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		//setContentView(R.layout.main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.feedback_header);

	}

}
