package com.example.location;

import java.util.List;

import com.androidquery.AQuery;
import com.sayit.common.model.Place;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlacesListAdapter extends ArrayAdapter<Place> {

	private List<Place> places;
	private Context context;

	public PlacesListAdapter(Context context,
			int textViewResourceId, List<Place> places) {
		super(context, textViewResourceId, places);
		this.places = places;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Place place = places.get(position);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater
				.inflate(R.layout.places_list_row, parent, false);
		TextView nameView = (TextView) rowView.findViewById(R.id.placeName);
		TextView locationView = (TextView) rowView
				.findViewById(R.id.placeLocation);
		ImageView iconView = (ImageView)rowView.findViewById(R.id.placeIcon);
		nameView.setText(place.getName());
		locationView.setText(place.getVicinity());

	    AQuery aq = new AQuery(rowView);

	     aq.id(iconView).image(place.getIcon(), true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);

	    return rowView;
	}

}
