package com.example.location;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;

public class DisplayCoupon extends Activity {

	ArrayList<String> coupon = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		coupon = (ArrayList<String>) (getIntent().getExtras()
				.getSerializable("coupon"));
		setContentView(R.layout.display_coupon);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.coupon_header);

		Button back = (Button) findViewById(R.id.backButton);

		final WebView webView = (WebView) findViewById(R.id.webview);

		Button saveCoupon = (Button) findViewById(R.id.savecoupon);
		saveCoupon.setTextColor(Color.WHITE);
		saveCoupon.setTextSize(20);
		webView.loadData(coupon.get(0), "text/html", "utf-8");

		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});


		saveCoupon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				takeScreenshot();
			}
		});

	}

	private void takeScreenshot() {
		Date now = new Date();
		android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
		String dirName = "Say_it_Coupons";
		File myDirectory = new File(Environment.getExternalStorageDirectory(),
				dirName);

		if (!myDirectory.exists()) {
			myDirectory.mkdirs();
		}

		try {
			// image naming and path to include sd card appending name you
			// choose for file
			String mPath = Environment.getExternalStorageDirectory().toString()
					+ "/" + dirName + "/coupon_" + now + ".jpg";

			// create bitmap screen capture
			View v1 = getWindow().getDecorView().getRootView();
			v1.setDrawingCacheEnabled(true);
			Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
			v1.setDrawingCacheEnabled(false);

			File imageFile = new File(mPath);

			FileOutputStream outputStream = new FileOutputStream(imageFile);
			int quality = 100;
			bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream);
			outputStream.flush();
			outputStream.close();
			MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
					"Screen", "screen");

			new AlertDialog.Builder(DisplayCoupon.this)
					.setTitle("Congratulations ! ")
					.setMessage("You have successfully saved the coupon.")
					.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).setIcon(android.R.drawable.ic_dialog_info)
					.show();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public boolean goBack() {
		Intent myIntent = new Intent(getApplicationContext(),
				FeedBackActivity.class);
		startActivityForResult(myIntent, 0);
		return true;

	}

}
