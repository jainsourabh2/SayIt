package com.sayit.common.model;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Place implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -687877100283908721L;
	private String id;
	private String icon;
	private String name;
	private String vicinity;
	private Double latitude;
	private Double longitude;
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public static Place jsonToPontoReferencia(JSONObject pontoReferencia) {
		try {
			Place result = new Place();
			JSONObject geometry = (JSONObject) pontoReferencia.get("geometry");
			JSONObject location = (JSONObject) geometry.get("location");
			JSONArray types  = (JSONArray)pontoReferencia.get("types");
			result.setLatitude((Double) location.get("lat"));
			result.setLongitude((Double) location.get("lng"));
			result.setIcon(pontoReferencia.getString("icon"));
			result.setName(pontoReferencia.getString("name"));
			result.setVicinity(pontoReferencia.getString("vicinity"));
			result.setType(types.getString(0));
			
			return result;
		} catch (JSONException ex) {
			Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	@Override
	public String toString() {
		return "Place{" + "id=" + id + ", icon=" + icon + ", name=" + name
				+ ", latitude=" + latitude + ", longitude=" + longitude + '}';
	}

}
